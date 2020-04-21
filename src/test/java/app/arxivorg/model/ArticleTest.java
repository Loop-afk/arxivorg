package app.arxivorg.model;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static app.arxivorg.model.Article.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArticleTest {
    //Correspond entre autre au fichier verySmallTest.atom avec les 2 articles : ReZero et Multi-SimLex. Attention readFile ajoute dans l'ordre inverse de rencontre.
    LinkedList<Article> test = readFile(Article.getArticlesFromArXiv("Multi-SimLex, ReZero"));
    Article article = test.get(0);

    public ArticleTest() throws Exception {
    }

    @Test
    public void testParsing() throws ParseException {

        String id = "http://arxiv.org/abs/2003.04887v1";
        assertEquals(id,article.getId());

        String title = "ReZero is All You Need: Fast Convergence at Large Depth";
        assertEquals(title, article.getTitle());

        String authors = "Thomas Bachlechner, Bodhisattwa Prasad Majumder, Huanru Henry Mao, Garrison W. Cottrell, Julian McAuley";
        assertEquals(authors, article.getAuthor().toString());

        String summary = "Deep networks have enabled significant performance gains across domains, but\n" +
                "they often suffer from vanishing/exploding gradients. This is especially true\n" +
                "for Transformer architectures where depth beyond 12 layers is difficult to\n" +
                "train without large datasets and computational budgets. In general, we find\n" +
                "that inefficient signal propagation impedes learning in deep networks. In\n" +
                "Transformers, multi-head self-attention is the main cause of this poor signal\n" +
                "propagation. To facilitate deep signal propagation, we propose ReZero, a simple\n" +
                "change to the architecture that initializes an arbitrary layer as the identity\n" +
                "map, using a single additional learned parameter per layer. We apply this\n" +
                "technique to language modeling and find that we can easily train\n" +
                "ReZero-Transformer networks over a hundred layers. When applied to 12 layer\n" +
                "Transformers, ReZero converges 56% faster on enwiki8. ReZero applies beyond\n" +
                "Transformers to other residual networks, enabling 1,500% faster convergence for\n" +
                "deep fully connected networks and 32% faster convergence for a ResNet-56\n" +
                "trained on CIFAR 10.";
        assertEquals(summary,article.getSummary());

        String lien = "https://arxiv.org/abs/2003.04887v1";
        assertEquals(lien,article.getLinkOfArticle());

        String pdf = "https://arxiv.org/pdf/2003.04887v1";
        assertEquals(pdf,article.getLinkOfArticlePDF());

        String categories ="cs.LG, cs.CL, stat.ML";
        assertEquals(categories,article.getCategory().toString().replace("[","").replace("]",""));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        Date datePublication = sdf.parse("2020-03-10 17:58:01");
        assertEquals(datePublication,toDate(article.getDateOfPublication()));
    }

    @Test
    public void testSort(){
        LinkedList<Article> testCopy = test;
        assertEquals(testCopy.get(0),test.get(0));
        assertEquals(testCopy.get(1), test.get(1));
        Article.sortByDateOfPublication(testCopy);
        assertEquals(testCopy.get(0),test.get(0));
        assertEquals(testCopy.get(1), test.get(1));

        LinkedList<Article> testCopy2 = new LinkedList<>();
        testCopy2.add(test.get(1));
        testCopy2.add(test.get(0));
        assertEquals(testCopy2.get(0),test.get(1));
        assertEquals(testCopy2.get(1), test.get(0));
        Article.sortByDateOfUpdate(testCopy);
        assertEquals(testCopy.get(0),test.get(0));
        assertEquals(testCopy.get(1), test.get(1));
    }

    @Test
    public void testGetAllAuthors(){
        String allAuthors = "Thomas Bachlechner, Bodhisattwa Prasad Majumder, Huanru Henry Mao, Garrison W. Cottrell, Julian McAuley, Ivan Vuli\u0107, Simon Baker, Edoardo Maria Ponti, Ulla Petti, Ira Leviant, Kelly Wing, Olga Majewska, Eden Bar, Matt Malone, Thierry Poibeau, Roi Reichart, Anna Korhonen";
        assertEquals(allAuthors,Article.getAllAuthors(test).toString());
        assertEquals(allAuthors,test.get(0).getAuthor().toString()+ ", "+ test.get(1).getAuthor().toString());
    }

    @Test
    public void testGetAllCategories(){
        String allCategories = "[cs.LG, cs.CL, stat.ML]";
        assertEquals(allCategories, Article.getAllCategories(test).toString());
    }

    @Test
    public void testFilters() throws ParseException {
        LinkedList<Article> filterTest1 = new LinkedList<>();
        filterTest1.add(test.get(1));
        assertEquals(filterTest1.get(0), Article.filteredByID(test, "2003.04866"));

        LinkedList<Article> filterTest2 = new LinkedList<>();
        filterTest2.add(test.get(0));
        assertEquals(filterTest2, Article.filteredByKeyword(test,"deep"));

        LinkedList<Article> filterTest3 = new LinkedList<>();
        filterTest3.add(test.get(1));
        assertEquals(filterTest3, Article.filteredByAuthors(test, Article.toArray("Eden Bar, Kelly Wing", ",")));

        LinkedList<Article> filterTest4 = new LinkedList<>();
        filterTest4.add(test.get(0));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        Date dateUpdated = sdf.parse("2020-03-10 17:20:00");
        assertEquals(filterTest4, Article.filterByDateOfUpdate(test,dateUpdated));


        Date datePublication = sdf.parse("2020-03-10 17:10:00");
        assertEquals(test,Article.filterByDateOfPublication(test,datePublication));

        assertEquals(test, Article.filterByCategory(test,"cs.CL"));
    }

    @Test
    public void testAPIRequest() throws Exception {
        LinkedList<Article> testElectron = readFile(getArticlesFromArXivWithLimitedNumber("electron",5));
        assertEquals(5, testElectron.size());
        LinkedList<Article> testElectronCopy = Article.filteredByKeyword(testElectron,"electron");
        assertEquals(testElectronCopy,testElectron);
        Article.sortByDateOfPublication(testElectron);
        for(int i = 0; i<testElectron.size()-1; i++){
            assertTrue(Article.toDate(testElectron.get(i).getDateOfPublication()).after(Article.toDate(testElectron.get(i+1).getDateOfPublication())));
        }

        LinkedList<Article> testProteins = readFile(getArticlesFromArXiv("protein"));
        assertEquals(10, testProteins.size());
        LinkedList<Article> testProteinsCopy = Article.filteredByKeyword(testProteins,"protein");
        assertEquals(testProteinsCopy,testProteins);
        LinkedList<Article> testProteinsCopy2 = Article.filteredByKeyword(testElectron,"gliehgm");
        assertEquals(testProteinsCopy2, new LinkedList<>());
    }

    @Test
    public void testDownload() {
        //TODO: Comment tester le bon fonctionnement du téléchargement?
    }
}
