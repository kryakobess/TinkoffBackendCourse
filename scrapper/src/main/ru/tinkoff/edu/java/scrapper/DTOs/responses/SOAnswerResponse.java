package scrapper.DTOs.responses;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

public record SOAnswerResponse(Answer[] items) implements SOItemsDescriptionInterface {
    @Override
    public String getItemDescription(int itemIndex) {
        return items[itemIndex].getDescription();
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }

    @Override
    public boolean itemHasNewUpdates(int itemIndex, Timestamp lastUpdated) {
        Timestamp eventAt = Timestamp.from(items[itemIndex].last_activity_date.toInstant());
        return eventAt.after(lastUpdated);
    }

    @Override
    public boolean hasItems() {
        return items != null && items.length != 0;
    }

    public record Answer(AnswerOwner owner, OffsetDateTime creation_date, OffsetDateTime last_activity_date, int score){
        record AnswerOwner(int accept_rate, String display_name, String link){}

        public String getDescription(){
            String descBeginning = "New answer";
            if (!creation_date.equals(last_activity_date)) descBeginning = "New activity in answer";
            return String.format("%s from %s(%s) with accept_rate = %d\nAnswer score = %d\n",
                    descBeginning, owner.display_name, owner.link, owner.accept_rate, score);
        }
    }
}
