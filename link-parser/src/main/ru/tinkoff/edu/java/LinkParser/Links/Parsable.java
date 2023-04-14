package LinkParser.Links;

import java.net.URL;

public sealed interface Parsable permits GithubLink, StackOverflowLink{
    URL getURL();
}
