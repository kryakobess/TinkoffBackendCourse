package scrapper.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import scrapper.DTOs.responses.SOAnswerResponse;
import scrapper.DTOs.responses.SOCommentResponse;
import scrapper.DTOs.responses.StackOverflowQuestionsResponse;

public interface StackOverflowClient {
    @GetExchange(url = "/2.3/questions/{ids}/comments?order=desc&sort=creation&site=stackoverflow")
    SOCommentResponse getQuestionComments(@PathVariable long ids);

    @GetExchange(url = "/2.3/questions/{ids}/answers?order=desc&sort=activity&site=stackoverflow")
    SOAnswerResponse getQuestionAnswers(@PathVariable long ids);
}
