package app.arxivorg.model;

public class Arrete {
    private final Node nodeSource;
    private final Node nodeTarget;
    private final int distance;

    public Arrete(Node nodeSource, Node nodeTarget, int distance){
        this.nodeSource = nodeSource;
        this.nodeTarget = nodeTarget;
        this.distance = distance;
    }

    public Node getNodeSource() {
        return nodeSource;
    }

    public Node getNodeTarget() {
        return nodeTarget;
    }

    public int getDistance() {
        return distance;
    }

}