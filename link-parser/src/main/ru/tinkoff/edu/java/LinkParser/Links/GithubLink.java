package LinkParser.Links;

import java.net.URL;

public record GithubLink(URL url, String user, String repo) implements Parsable {
    @Override
    public URL getURL() {
        return url;
    }
}
