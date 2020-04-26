package app.arxivorg.model;

public class Node {

    private final String authorName;

    public Node(String input){
        this.authorName = input;
    }

    public String getName() {
        return authorName;
    }
}
