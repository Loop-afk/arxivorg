package app.arxivorg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    LinkedList<Node> nodes;
    static LinkedList<Arrete> arretes;

    public Graph(LinkedList<Node> nodes, LinkedList<Arrete> arretes) {
        Graph.arretes = arretes;
        this.nodes = nodes;
    }

    public Graph() {
        this.nodes = new LinkedList<>();
        arretes = new LinkedList<>();
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public LinkedList<Arrete> getArretes() {
        return arretes;
    }

    public static boolean contains(List<Node> nodes){
        for (Arrete arrete : arretes) {
            if(nodes.get(0).getName().equals(arrete.getNodeSource().getName()) && nodes.get(1).getName().equals(arrete.getNodeTarget().getName()) && nodes.size() == 2) {
                return true;
            }
        }
        return false;
    }

    //Permet de générer un graphe avec comme sommet un nom d'auteur et comme arrête un lien de co-auteur sur un article ou plus.
    public static Graph generateGraph(LinkedList<Article> articles) {
        Authors authors = Article.getAllAuthors(articles);
        Graph result = new Graph();
        for (int i = 1; i <= authors.getData().size(); i++) {
            String character = Integer.toString(i);
            Node node = new Node(authors.getData().get(i));
            result.getNodes().add(node);
        }
        for (Article article : articles) {
            for (int k = 0; k < article.getAuthor().getData().size(); k++) {
                for (int j = 0; j < article.getAuthor().getData().size(); j++) {
                    Node node1 = new Node(article.getAuthor().getData().get(k));
                    Node node2 = new Node(article.getAuthor().getData().get(j));

                    List<Node> nodePair = Arrays.asList(node1, node2);

                    if (node1.getName().equals(node2.getName()) || contains(nodePair)) {
                        k--;
                    } else {
                        result.getArretes().add(new Arrete(node1, node2, 1));
                    }
                }
            }
        }
        return result;
    }

    //Permet d'obtenir la liste des voisins d'un noeud, donc tous les co-auteurs d'un scientifique passé en paramètre.
    public static List<Node> getNeighbors(String author, Graph graph) {
        Node node = new Node(author);
        List<Node> result = new ArrayList<>();
        for (Arrete arrete : graph.getArretes()) {
            if (arrete.getNodeSource().getName().equals(node.getName())) {
                result.add(arrete.getNodeTarget());
            }
        }
        return result;
    }

    //Permet de savoir si deux auteurs sont connectés pour un certain graphe, donc dans une certaine liste d'article.
    //TODO: Optimisable.
    public static boolean areConnected(String author1, String author2, Graph graph){
        List<Node> neighbors = getNeighbors(author1,graph);
        for (int i = 0; i < graph.getNodes().size(); i++){
            for (Node neighbor : neighbors){
                if (neighbor.getName().equals(author2)){
                return true;
                }

                neighbors.addAll(getNeighbors(neighbor.getName(),graph));
            }
        }
        return false;
    }
}
