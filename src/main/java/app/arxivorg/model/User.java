package app.arxivorg.model;

import java.util.Date;
import java.util.LinkedList;

public class User {
    private final String username;
    private String password;
    private LinkedList<Article> favoris;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.favoris = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList<Article> getFavoris() {
        return favoris;
    }

    public void addArticle(Article article){
        this.favoris.addLast(article);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", favoris=" + favoris +
                '}';
    }
}
