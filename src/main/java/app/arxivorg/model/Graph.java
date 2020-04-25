package app.arxivorg.model;

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

    public boolean contains(List<Node> nodes){
        for (Arrete arrete : arretes) {
            if(nodes.get(0).getName().equals(arrete.getNodeSource().getName()) && nodes.get(1).getName().equals(arrete.getNodeTarget().getName()) && nodes.size() == 2) {
                return true;
            }
        }
        return false;
    }
    public Graph generateGraph(LinkedList<Article> articles){
        Authors authors = Article.getAllAuthors(articles);
        Graph result = new Graph();
        for (int i = 1; i <= authors.getData().size(); i++){
            String character = Integer.toString(i);
            Node node = new Node(authors.getData().get(i));
            result.nodes.add(node);
        }

        for (int k = 0; k < authors.getData().size(); k++) {
            for (int j = 0; j < authors.getData().size(); j++) {
            Node node1 = new Node(authors.getData().get(k));
            Node node2 = new Node(authors.getData().get(j));

            List<Node> nodePair = Arrays.asList(node1,node2);

            if (node1.getName().equals(node2.getName()) || this.contains(nodePair)) {
                k--;
            } else {
                result.arretes.add(new Arrete(node1,node2,1));
             }
            }
        }
        return result;
    }

    
}
