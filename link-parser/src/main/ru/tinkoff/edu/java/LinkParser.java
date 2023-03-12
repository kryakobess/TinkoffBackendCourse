import Links.Parsable;
import Parsers.AbstractHandler;
import Parsers.GithubHandler;
import Parsers.HandlerOrder;
import Parsers.StackOverflowHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

public class LinkParser {
    public static Parsable parse(String url) throws MalformedURLException {
        URL link = new URL(url);
        return parse(link);
    }
    public static Parsable parse(URL url){
        return HandlerOrder.buildChain().handle(url);
    }

    public static void main(String[] args) throws MalformedURLException {
        LinkParser.parse("https://stackoverflow.com/search?q=unsupported%20link");
    }
}
