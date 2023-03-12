package Parsers;

import Links.Parsable;

import java.net.URL;

public abstract class AbstractHandler {
    protected AbstractHandler nextHandler;

    public AbstractHandler(AbstractHandler next){
        this.nextHandler = next;
    }
    public abstract Parsable handle(URL url);
}
