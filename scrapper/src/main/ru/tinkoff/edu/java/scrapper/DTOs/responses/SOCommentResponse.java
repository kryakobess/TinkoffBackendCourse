package scrapper.DTOs.responses;


import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

public record SOCommentResponse(Comment[] items) implements SOItemsDescriptionInterface {
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
        Timestamp eventAt = Timestamp.from(items[itemIndex].creation_date.toInstant());
        return eventAt.after(lastUpdated);
    }

    @Override
    public boolean hasItems() {
        return items != null && items.length != 0;
    }

    public record Comment(CommentOwner owner, OffsetDateTime creation_date, int score){
        record CommentOwner(int accept_rate, String display_name, String link){}

        public String getDescription(){
            return String.format("New comment from %s(%s) with accept rate = %d\nComment score = %d\n",
                    owner.display_name, owner.link, owner.accept_rate, score);
        }
    }
}
