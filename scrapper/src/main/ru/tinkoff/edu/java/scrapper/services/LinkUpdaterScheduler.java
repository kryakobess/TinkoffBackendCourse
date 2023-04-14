package scrapper.services;

import LinkParser.LinkParser;
import LinkParser.Links.GithubLink;
import LinkParser.Links.StackOverflowLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.DTOs.requests.TgBotLinkUpdateRequest;
import scrapper.DTOs.responses.SOCommentResponse;
import scrapper.DTOs.responses.SOItemsDescriptionInterface;
import scrapper.Repositories.JdbcLinkDao;
import scrapper.Repositories.JdbcTelegramUserDao;

import scrapper.domains.Link;

import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
public class LinkUpdaterScheduler {

    final GitHubClient gitHubClient;
    final StackOverflowClient stackOverflowClient;
    final JdbcLinkDao linkDao;
    final JdbcTelegramUserDao userDao;

    final TelegramBotClient botClient;

    private static final int EVENTS_TO_CHECK_COUNT = 15;

    public LinkUpdaterScheduler(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, JdbcLinkDao linkDao, JdbcTelegramUserDao userDao, TelegramBotClient botClient) {
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.linkDao = linkDao;
        this.userDao = userDao;
        this.botClient = botClient;
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update(){
        var link = linkDao.getLatestUpdatedLink();
        if (link != null){
            checkForUpdates(link);
        }
    }

    private void checkForUpdates(Link link) {
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

    @Transactional
    void processGitHubUpdates(String owner, String repo, Link dbData) {
        var events = gitHubClient.getRepoData(owner, repo);
        if (events != null) {
            for (int i = Math.min(EVENTS_TO_CHECK_COUNT, events.length-1); i >= 0; --i) {
                var e = events[i];
                if (hasNewUpdate(e.created_at(), dbData.getLastUpdate())) {
                    String desc = e.getEventDescription();
                    sendUpdateToBot(dbData, desc);
                }
            }
        }
        dbData.setLastUpdate(Timestamp.from(Instant.now()));
        linkDao.updateLinkById(dbData);
    }

    @Transactional
    void processStackOverflowUpdates(long questionId, Link dbData){
        var comments = stackOverflowClient.getQuestionComments(questionId);
        var answers = stackOverflowClient.getQuestionAnswers(questionId);

        StringBuilder description = new StringBuilder()
                .append(getItemsDescription(comments, dbData))
                .append(getItemsDescription(answers, dbData));;

        if (!description.toString().isBlank()){
            sendUpdateToBot(dbData, description.toString());
        }
        dbData.setLastUpdate(Timestamp.from(Instant.now()));
        linkDao.updateLinkById(dbData);
    }

    String getItemsDescription(SOItemsDescriptionInterface items, Link dbData){
        StringBuilder result = new StringBuilder();
        if (items.hasItems()){
            for (int i = Math.min(items.getItemsCount()-1, EVENTS_TO_CHECK_COUNT); i >= 0; i--){
                if (items.itemHasNewUpdates(i, dbData.getLastUpdate())){
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
        var user = userDao.getById(dbData.getTgUserId());
        botClient.sendUpdate(
                TgBotLinkUpdateRequest.builder()
                        .id(Math.toIntExact(dbData.getId()))
                        .description(description)
                        .url(URI.create(dbData.getLink()))
                        .tgChatIds(List.of(Math.toIntExact(user.getChatId())))
                        .build()
        );
    }
}
