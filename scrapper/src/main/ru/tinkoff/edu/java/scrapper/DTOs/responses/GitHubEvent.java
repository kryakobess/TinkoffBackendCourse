package scrapper.DTOs.responses;

import java.time.OffsetDateTime;
import java.util.List;

public record GitHubEvent(
        String type,
        Actor actor,
        Payload payload,
        OffsetDateTime created_at
){
    record Actor(String login){}
    record Payload(List<Commit> commits){
        record Commit(Author author, String message){
            record Author(String name){}

            String getNotes(){
                return "Commit:\n" +
                        "By " + author.name + "\n" +
                        "With message: " + message;
            }
        }

        String commitsNotes(){
            StringBuilder result = new StringBuilder();
            for (var c : commits){
                result.append(c.getNotes()).append("\n\n");
            }
            return result.toString();
        }
    }

    public String getEventDescription(){
        return type + " was executed by " + actor.login + " at" + created_at.toString() + "\n" +
                "Event notes:\n\n" + payload.commitsNotes();
    }
}
