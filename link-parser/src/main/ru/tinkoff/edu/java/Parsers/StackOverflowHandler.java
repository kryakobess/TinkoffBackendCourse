package Parsers;

import Links.Parsable;
import Links.StackOverflowLink;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class StackOverflowHandler extends AbstractHandler{

    public StackOverflowHandler(AbstractHandler next){
        super(next);
    }

    public StackOverflowHandler(){
        super();
    }

    @Override
    public Parsable handle(URL url) {
        if (url.getHost().equals("stackoverflow.com")){
            Path path = Paths.get(url.getPath());
            if (path.getNameCount() >= 2){
                if (path.getName(0).toString().equals("questions")){
                    return new StackOverflowLink(url, Long.parseLong(path.getName(1).toString()));
                }
            }
            return null;
        }
        return nextHandler.handle(url);
    }
}
