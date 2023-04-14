import LinkParser.LinkParser;
import LinkParser.Links.GithubLink;
import LinkParser.Links.Parsable;
import LinkParser.Links.StackOverflowLink;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ParseTest {
    @Test
    public void gitHubParse_CorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        GithubLink exp = new GithubLink(new URL("https://github.com/sanyarnd/tinkoff-java-course-2022/"), "sanyarnd", "tinkoff-java-course-2022");
        LinkParser.printParseResult("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        Assert.assertEquals(exp, res);
    }

    @Test
    public void SOParse_CorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        LinkParser.printParseResult("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        Assert.assertEquals(1642028, ((StackOverflowLink)res).questionId());
    }

    @Test
    public void SOParse_IncorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://stackoverflow.com/search?q=unsupported%20link\n");
        try{
            LinkParser.printParseResult("https://stackoverflow.com/search?q=unsupported%20link");
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        };
        Assert.assertNull(res);
    }

    @Test
    public void UnknownLink_IncorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://habr.com/ru/post/444982/");
        Assert.assertNull(res);
    }

    @Test
    public void NotLink_IncorrectTest() throws MalformedURLException {
        try {
            Parsable res = LinkParser.parse("something");
        } catch (MalformedURLException exception) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }


}
