package scrapper.DTOs.responses;

import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionsResponse(List<Question> items) {
    public record Question(OffsetDateTime last_activity_date){}
}
