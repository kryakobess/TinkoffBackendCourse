package scrapper.DTOs.responses;

import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(OffsetDateTime updated_at) {
}
