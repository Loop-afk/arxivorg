package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import static app.arxivorg.model.Article.readFile;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorsTest {

    private Authors authors = new Authors(Arrays.asList("Martin Dupont", "Marie Martin", "François Cordonnier"));

    @Test
    public void testToString() {
        String expected = "Martin Dupont, Marie Martin, François Cordonnier";
        assertEquals(expected, authors.toString());
    }

    @Test
    public void testGetAllArticles() throws Exception {
        LinkedList<Article> expected = readFile(Article.getArticlesFromArXiv("Multi-SimLex"));
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), Authors.getAllArticles("Eden Bar").get(i).getId());
        }
    }
}

