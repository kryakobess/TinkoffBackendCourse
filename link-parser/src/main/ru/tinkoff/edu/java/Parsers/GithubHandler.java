package Parsers;

import Links.GithubLink;
import Links.Parsable;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class GithubHandler extends AbstractHandler{

    public GithubHandler(AbstractHandler next){
        super(next);
    }

    public GithubHandler(){
        super();
    }

    @Override
    public Parsable handle(URL url) {
        if (url.getHost().equals("github.com")){
            Path path = Paths.get(url.getPath());
            if (path.getNameCount() >= 2){
                String user = path.getName(0).toString();
                String repo = path.getName(1).toString();
                return new GithubLink(url, user, repo);
            }
            else return null;
        }
        return nextHandler.handle(url);
    }
}
