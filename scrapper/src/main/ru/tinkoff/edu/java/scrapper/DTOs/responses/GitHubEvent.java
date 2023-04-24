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
    record Payload(List<Commit> commits, Issue issue){

        record Issue(String title, String state, User user){

            record User(String login){}

            String getNotes(){
                return String.format(
                        """
                                Issue:
                                With title: %s
                                By %s
                                State: %s""",
                        user.login, title, state);
            }
        }
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
                result.append(c.getNotes()).append("\n");
            }
            return result.toString();
        }
    }

    public String getEventDescription(){
        String issueNotes = payload.issue == null ? "" : payload.issue.getNotes() +"\n";
        String commitsNotes = payload.commits == null ? "" : payload.commitsNotes();
        String notes = (!commitsNotes.isBlank() || !issueNotes.isBlank()) ?
                "Event notes:\n" + issueNotes + "" + commitsNotes : "";
        return String.format("%s was executed by %s at %s\n%s",
                type, actor.login, created_at, notes);
    }
}
