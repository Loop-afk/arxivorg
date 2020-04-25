package app.arxivorg.model;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import static app.arxivorg.model.Article.*;

public class Main {

    public static void main(String[] args) throws Exception {
      LinkedList<Article> list1 = readFile(getArticlesFromArXivWithLimitedNumber("electron, impact",5));
      LinkedList<Article> list2 = readFile(getArticlesFromArXivWithLimitedNumber("electron, impact",5));

     /*   for(Article list: list1){
            System.out.println(list.getSummary());*/

        System.out.println(list1.toString().contains(list2.get(0).getId()));
        }
    }

