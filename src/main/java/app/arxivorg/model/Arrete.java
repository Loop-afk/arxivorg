package app.arxivorg.model;

public class Arrete {
    private final Node nodeSource;
    private final Node nodeTarget;

    public Arrete(Node nodeSource, Node nodeTarget){
        this.nodeSource = nodeSource;
        this.nodeTarget = nodeTarget;
    }

    public Node getNodeSource() {
        return nodeSource;
    }

    public Node getNodeTarget() {
        return nodeTarget;
    }


}