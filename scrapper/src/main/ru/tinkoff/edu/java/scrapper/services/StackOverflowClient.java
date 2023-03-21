package scrapper.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import scrapper.DTOs.responses.StackOverflowQuestionsResponse;

public interface StackOverflowClient {
    @GetExchange(url = "/2.3/questions/{ids}?order=desc&sort=activity&site=stackoverflow")
    StackOverflowQuestionsResponse getQuestionData(@PathVariable long ids);
}
