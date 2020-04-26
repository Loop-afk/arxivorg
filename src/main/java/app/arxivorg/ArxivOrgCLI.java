package app.arxivorg;

import app.arxivorg.model.Article;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ArxivOrgCLI extends Article {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the arXiv organizer!");
        LinkedList<Article> listOfArticle = new LinkedList<>(Article.readFile(Article.getArticlesFromArXivWithLimitedNumber("", 5)));
        //        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");
        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);
        String[] expression = scanner.next().split(" ");
        while(scanner.hasNext()) {

            if(expression[0].equals("list")){
                switch (scanner.next()){
                    case "search":
                        // j'ai limité 10 ici le nombre de recher pour la simplicité de lecture des résultats
                        listOfArticle = readFile(getArticlesFromArXivWithLimitedNumber(scanner.next(), 10));
                        System.out.println("Liste actualisée, affichez là avec la commande: list show");
                        break;
                    case "-c":
                        LinkedList<Article> filteredByCategories;
                        filteredByCategories = filterByCategory(listOfArticle, scanner.next());
                        System.out.println(filteredByCategories);
                        break;
                    case "-a":
                        System.out.println(getAllArticles(scanner.next() + scanner.next()));
                        break;
                    case "exit":
                        System.out.println("Merci d'avoir utilisé l'invite de commande");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Mauvais Paramètres ! - Veuillez relancer le programme");
                        break;
                }
            }
        }
        scanner.close();
        System.exit(0);
    }
}
