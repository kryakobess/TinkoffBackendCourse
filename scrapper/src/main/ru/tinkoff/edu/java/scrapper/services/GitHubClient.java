package scrapper.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import scrapper.DTOs.responses.GitHubRepositoryResponse;

public interface GitHubClient {
    @GetExchange(url = "/repos/{owner}/{repo}", accept =  "application/vnd.github.v3+json")
    GitHubRepositoryResponse getRepoData(@PathVariable String owner, @PathVariable String repo);
}
