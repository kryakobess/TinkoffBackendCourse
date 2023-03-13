import Links.GithubLink;
import Links.Parsable;
import Links.StackOverflowLink;

import java.net.MalformedURLException;
import java.net.URL;


public class LinkParser {

    public static Parsable parse(String url) throws MalformedURLException {
        URL link = new URL(url);
        return parse(link);
    }

    public static Parsable parse(URL url){
        return ChainBuilder.buildChain().handle(url);
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
