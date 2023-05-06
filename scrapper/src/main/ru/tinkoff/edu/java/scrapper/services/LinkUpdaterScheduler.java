package scrapper.services;

import LinkParser.LinkParser;
import LinkParser.Links.GithubLink;
import LinkParser.Links.StackOverflowLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.DTOs.requests.TgBotLinkUpdateRequest;
import scrapper.DTOs.responses.SOItemsDescriptionInterface;
import scrapper.domains.Link;

import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
public class LinkUpdaterScheduler {

    final GitHubClient gitHubClient;
    final StackOverflowClient stackOverflowClient;
    final LinkService linkService;
    final TgUserService tgUserService;

    final UpdateSender updateSender;

    private static final int EVENTS_TO_CHECK_COUNT = 15;

    public LinkUpdaterScheduler(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient,
                                LinkService linkService, TgUserService tgUserService, UpdateSender updateSender) {
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.linkService = linkService;
        this.tgUserService = tgUserService;
        this.updateSender = updateSender;
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update(){
        try {
            var link = linkService.getLatestUpdatedLink();
            checkForUpdates(link);
            link.setLastUpdate(Timestamp.from(Instant.now()));
            linkService.updateLinkById(link);
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    private void checkForUpdates(Link link) {
        log.info("Checking for updates of " + link.getLink());
        try {
            var parseResult = LinkParser.parse(new URL(link.getLink()));
            switch (parseResult) {
                case GithubLink githubLink -> processGitHubUpdates(githubLink.user(), githubLink.repo(), link);
                case StackOverflowLink stackOverflowLink -> processStackOverflowUpdates(stackOverflowLink.questionId(), link);
            }
        } catch (Exception ex){
            log.info("Exception: " + ex.getMessage());
        }
    }

    void processGitHubUpdates(String owner, String repo, Link dbData) {
        log.info("Fetching github events for link " + dbData.getLink());
        var events = gitHubClient.getRepoData(owner, repo);
        if (events != null) {
            for (int i = Math.min(EVENTS_TO_CHECK_COUNT, events.length-1); i >= 0; --i) {
                var e = events[i];
                System.out.println(e.created_at());
                if (hasNewUpdate(e.created_at(), dbData.getLastUpdate())) {
                    log.info("new update on " + dbData.getLink());
                    String desc = e.getEventDescription();
                    sendUpdateToBot(dbData, desc);
                }
            }
        }
    }

    void processStackOverflowUpdates(long questionId, Link dbData){
        log.info("Fetching SO comments from " + dbData.getLink());
        var comments = stackOverflowClient.getQuestionComments(questionId);
        log.info("Fetching SO answers from " + dbData.getLink());
        var answers = stackOverflowClient.getQuestionAnswers(questionId);

        StringBuilder description = new StringBuilder()
                .append(getItemsDescription(comments, dbData))
                .append(getItemsDescription(answers, dbData));

        if (!description.toString().isBlank()){
            sendUpdateToBot(dbData, description.toString());
        }
    }

    String getItemsDescription(SOItemsDescriptionInterface items, Link dbData){
        StringBuilder result = new StringBuilder();
        if (items.hasItems()){
            for (int i = Math.min(items.getItemsCount()-1, EVENTS_TO_CHECK_COUNT); i >= 0; i--){
                if (items.itemHasNewUpdates(i, dbData.getLastUpdate())){
                    log.info("new update on " + dbData.getLink());
                    result.append(items.getItemDescription(i));
                }
            }
        }
        return result.toString();
    }

    private boolean hasNewUpdate(OffsetDateTime eventTime, Timestamp lastUpdatedTime){
        Timestamp eventAt = Timestamp.from(eventTime.toInstant());
        return eventAt.after(lastUpdatedTime);
    }

    private void sendUpdateToBot(Link dbData, String description){
        try {
            log.info("Sending update to bot");
            var user = tgUserService.getUserById(dbData.getTgUserId());
            updateSender.send(
                    TgBotLinkUpdateRequest.builder()
                            .id(Math.toIntExact(dbData.getId()))
                            .description(description)
                            .url(URI.create(dbData.getLink()))
                            .tgChatIds(List.of(Math.toIntExact(user.getChatId())))
                            .build()
            );
            log.info("Update has been sent to " + user.getChatId());
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
