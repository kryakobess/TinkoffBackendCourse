package Links;

import java.net.URL;

public record StackOverflowLink(URL url, long questionId) implements Parsable {
    @Override
    public URL getURL() {
        return url;
    }
}
