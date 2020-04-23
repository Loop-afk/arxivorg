package app.arxivorg.model;

import java.util.Date;
import java.util.LinkedList;

public class User {
    private final String username;
    private String password;
    private final Date dateOfCreation;
    private LinkedList<Article> favoris;

    public User(String username, String password, Date dateOfCreation) {
        this.username = username;
        this.password = password;
        this.dateOfCreation = dateOfCreation;
        this.favoris = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public LinkedList<Article> getFavoris() {
        return favoris;
    }

    public void addArticle(Article article){
        this.favoris.addLast(article);
    }

}
