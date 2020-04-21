package app.arxivorg;

import app.arxivorg.model.Article;

import java.util.LinkedList;
import java.util.Scanner;

public class ArxivOrgCLI extends Article {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the arXiv organizer!");
        LinkedList<Article> listOfArticle = readFile("test.atom");
//        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");
        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.next();
        while(scanner.hasNext())
        {
            expression = scanner.next();
        switch (expression){
            case "list":
                System.out.println(getArticlesFromArXivWithLimitedNumber("java", 2));
                break;
            case "-c":
                System.out.println(getAllCategories(listOfArticle));
                break;
            case "-a":
                System.out.println(getAllAuthors(listOfArticle));
                break;

            default:
                System.out.println("Sorry, wrong parameters !");
                break;

        }
        }
        scanner.close();

    }
}
