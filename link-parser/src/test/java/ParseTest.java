import LinkParser.LinkParser;
import LinkParser.Links.GithubLink;
import LinkParser.Links.Parsable;
import LinkParser.Links.StackOverflowLink;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class ParseTest {
    @Test
    public void gitHubParse_CorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        GithubLink exp = new GithubLink(new URL("https://github.com/sanyarnd/tinkoff-java-course-2022/"), "sanyarnd", "tinkoff-java-course-2022");
        assertEquals(exp, res);
    }

    @Test
    public void SOParse_CorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        assertEquals(1642028, ((StackOverflowLink)res).questionId());
    }

    @Test
    public void SOParse_IncorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://stackoverflow.com/search?q=unsupported%20link\n");
        assertNull(res);
    }

    @Test
    public void UnknownLink_IncorrectTest() throws MalformedURLException {
        Parsable res = LinkParser.parse("https://habr.com/ru/post/444982/");
        assertNull(res);
    }

    @Test
    public void NotLink_IncorrectTest() throws MalformedURLException {
        try {
            Parsable res = LinkParser.parse("something");
        } catch (MalformedURLException exception) {
            assertTrue(true);
            return;
        }
        fail();
    }


}
