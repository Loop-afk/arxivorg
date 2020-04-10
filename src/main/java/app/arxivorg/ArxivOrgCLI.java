package app.arxivorg;

import app.arxivorg.model.Article;

import java.util.LinkedList;
import java.util.Scanner;

public class ArxivOrgCLI extends Article {
    public static void main(String[] args) {
        System.out.println("Welcome to the arXiv organizer!");
        LinkedList<Article> listOfArticle = readFile("test.atom");
//        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");
        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.next();

        switch (expression){
            case "-c":
                System.out.println(getAllCategories(listOfArticle));
                scanner.next();
            case "-a":
                System.out.println(getAllAuthors(listOfArticle));
                break;

            default:
                System.out.println("Sorry, wrong parameters !");
                break;

        }
        scanner.nextLine();
        scanner.close();
    }
}
