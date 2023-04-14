package scrapper.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import scrapper.DTOs.responses.GitHubEvent;

public interface GitHubClient {
    @GetExchange(url = "/repos/{owner}/{repo}/events", accept =  "application/vnd.github.v3+json")
    GitHubEvent[] getRepoData(@PathVariable String owner, @PathVariable String repo);
}
