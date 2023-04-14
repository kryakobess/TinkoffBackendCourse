package scrapper.DTOs.responses;

import java.sql.Timestamp;

public sealed interface SOItemsDescriptionInterface permits SOAnswerResponse, SOCommentResponse{
    String getItemDescription(int itemIndex);

    int getItemsCount();

    boolean itemHasNewUpdates(int itemIndex, Timestamp lastUpdated);

    boolean hasItems();
}
