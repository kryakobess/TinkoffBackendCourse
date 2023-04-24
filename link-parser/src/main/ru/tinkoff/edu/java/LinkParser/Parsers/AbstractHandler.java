package LinkParser.Parsers;

import LinkParser.Links.Parsable;

import java.net.URL;

public sealed abstract class AbstractHandler permits GithubHandler, StackOverflowHandler{

    protected AbstractHandler nextHandler;

    public AbstractHandler(AbstractHandler next){
        this.nextHandler = next;
    }

    public AbstractHandler(){}

    public void setNextHandler(AbstractHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    public abstract Parsable handle(URL url);
}
