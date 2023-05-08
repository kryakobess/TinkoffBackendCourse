package LinkParser;

import LinkParser.Links.Parsable;
import LinkParser.Parsers.AbstractHandler;
import LinkParser.Parsers.GithubHandler;
import LinkParser.Parsers.StackOverflowHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinkParser {

    private static final AbstractHandler[] HANDLER_ORDER = {
            new GithubHandler(),
            new StackOverflowHandler()
    };

    private static AbstractHandler buildChain() {
        Stack<AbstractHandler> stack = new Stack<>();
        for (int i = HANDLER_ORDER.length - 1; i >= 0; --i) {
            HANDLER_ORDER[i].setNextHandler(stack.isEmpty() ? null : stack.pop());
            stack.push(HANDLER_ORDER[i]);
        }
        return stack.pop();
    }

    public static Parsable parse(String url) throws MalformedURLException {
        URL link = new URL(url);
        return parse(link);
    }

    public static Parsable parse(URL url) {
        return buildChain().handle(url);
    }
}
