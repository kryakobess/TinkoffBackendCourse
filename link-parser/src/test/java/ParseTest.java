import Links.GithubLink;
import Links.Parsable;
import Links.StackOverflowLink;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ParseTest {
    @Test
    public void gitHubParseCorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        GithubLink exp = new GithubLink(new URL("https://github.com/sanyarnd/tinkoff-java-course-2022/"), "sanyarnd", "tinkoff-java-course-2022");
        LinkParser.printParseResult("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        Assert.assertEquals(exp, res);
    }

    @Test
    public void SOParseCorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        LinkParser.printParseResult("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        Assert.assertEquals(1642028, ((StackOverflowLink)res).questionId());
    }
    @Test
    public void SOParseIncorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://stackoverflow.com/search?q=unsupported%20link\n");
        try{
            LinkParser.printParseResult("https://stackoverflow.com/search?q=unsupported%20link");
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        };
        Assert.assertNull(res);
    }

}
