package app.arxivorg.model;

import java.util.LinkedList;

public class Article {
    String id;
    String dateOfPublication;
    String title;
    LinkedList<String> author;
    String summary;
    String comment;
    LinkedList<String> category;
    String linkOfArticle;
    String linkOfArticlePDF;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedList<String> getAuthor() {
        return author;
    }

    public void setAuthor(LinkedList<String> author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LinkedList<String> getCategory() {
        return category;
    }

    public void setCategory(LinkedList<String> category) {
        this.category = category;
    }

    public String getLinkOfArticle() {
        return linkOfArticle;
    }

    public void setLinkOfArticle(String linkOfArticle) {
        this.linkOfArticle = linkOfArticle;
    }

    public String getLinkOfArticlePDF() {
        return linkOfArticlePDF;
    }

    public void setLinkOfArticlePDF(String linkOfArticlePDF) {
        this.linkOfArticlePDF = linkOfArticlePDF;
    }
}
