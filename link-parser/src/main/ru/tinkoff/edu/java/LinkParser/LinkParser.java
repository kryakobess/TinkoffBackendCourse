package LinkParser;

import LinkParser.Links.GithubLink;
import LinkParser.Links.Parsable;
import LinkParser.Links.StackOverflowLink;
import LinkParser.Parsers.AbstractHandler;
import LinkParser.Parsers.GithubHandler;
import LinkParser.Parsers.StackOverflowHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;


public class LinkParser {

    private static final AbstractHandler[] handlerOrder = {
            new GithubHandler(),
            new StackOverflowHandler()
    };

    private static AbstractHandler buildChain(){
        Stack<AbstractHandler> stack = new Stack<>();
        for (int i = handlerOrder.length-1; i >= 0; --i){
            handlerOrder[i].setNextHandler(stack.isEmpty() ? null : stack.pop());
            stack.push(handlerOrder[i]);
        }
        return stack.pop();
    }

    public static Parsable parse(String url) throws MalformedURLException {
        URL link = new URL(url);
        return parse(link);
    }

    public static Parsable parse(URL url){
        return buildChain().handle(url);
    }

    public static void printParseResult(String url) throws MalformedURLException {
        printParseResult(new URL(url));
    }

    public static void printParseResult(URL url){
        switch (parse(url)){
            case GithubLink githubLink -> System.out.println(githubLink);
            case StackOverflowLink stackOverflowLink -> System.out.println(stackOverflowLink);
            case null -> throw new IllegalArgumentException("Unrecognized parsable");
        }
    }
}
