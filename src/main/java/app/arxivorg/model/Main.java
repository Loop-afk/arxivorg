package app.arxivorg.model;
import java.util.LinkedList;

import static app.arxivorg.model.Article.*;

public class Main {

    public static void main(String[] args) throws Exception {
        LinkedList<Article> list1 = readFile(getArticlesFromArXivWithLimitedNumber("electron, impact",100));
        //LinkedList<Article> list2 = readFile("verySmallTest.atom");
        System.out.println();
    }
}

