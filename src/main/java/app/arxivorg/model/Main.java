package app.arxivorg.model;


import java.util.LinkedList;

import static app.arxivorg.model.Article.infos;
import static app.arxivorg.model.Article.readFile;

public class Main {

    public static void main(String[] args) {
        LinkedList<Article> listOfArticle = readFile("test.atom");
    /*    for(Article articles : listOfArticle){
            System.out.println(articles.toString());
        }*/

        System.out.println("INFOS:" +infos.toString());
    }
}

