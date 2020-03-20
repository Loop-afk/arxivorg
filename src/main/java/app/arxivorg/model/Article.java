package app.arxivorg.model;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Article {
    String id;
    String dateOfUpdate;
    String dateOfPublication;
    String title;
    List<String> author;
    String summary;
    String comment;
    String category;
    String linkOfArticle;
    String linkOfArticlePDF;
    public static LinkedList<Article> infos = new LinkedList<>(readFile("test.atom"));

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(String dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
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

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    @NotNull
    public static LinkedList<Article> readFile(String pathname){
        LinkedList<Article> listOfArticle = new LinkedList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String stringDate;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(pathname);

            DocumentTraversal traversal = (DocumentTraversal) document;
            NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT,null,true);

            for(Node n = iterator.nextNode(); n != null; n = iterator.nextNode()){

                if(n.getNodeName().contentEquals("entry")){
                    NodeList nodeList = n.getChildNodes();
                    Article article = new Article();
                    List<String> authors = new LinkedList<>();
                    for (int i = 0; i < nodeList.getLength(); i++){

                        if(nodeList.item(i).getNodeName().contains("updated")) {
                            stringDate = nodeList.item(i).getTextContent();
                            stringDate = stringDate.replace("T", " ");
                            stringDate = stringDate.replace("Z", "");
                            article.setDateOfUpdate(stringDate);
                        }

                        if(nodeList.item(i).getNodeName().contains("published")) {
                            stringDate = nodeList.item(i).getTextContent();
                            stringDate = stringDate.replace("T", " ");
                            stringDate = stringDate.replace("Z","");
                            article.setDateOfPublication(stringDate);
                        }

                        if(nodeList.item(i).getNodeName().contains("id")){
                            article.setId(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("title")){
                            article.setTitle(nodeList.item(i).getTextContent());
                        }

                        if (nodeList.item(i).getNodeName().contains("author")) {
                            authors.add(nodeList.item(i).getTextContent().trim());
                            article.setAuthor(authors);
                        }

                        if(nodeList.item(i).getNodeName().contains("summary")){
                            article.setSummary(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("arxiv:comment")){
                            article.setComment(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("arxiv:primary_category")){
                            article.setCategory(nodeList.item(i).getAttributes().getNamedItem("xmlns:arxiv").getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("link")){
                            if(nodeList.item(i).getAttributes().getNamedItem("title") != null) {
                                article.setLinkOfArticlePDF(nodeList.item(i).getAttributes().getNamedItem("href").getTextContent());
                            }
                            else article.setLinkOfArticle(nodeList.item(i).getAttributes().getNamedItem("href").getTextContent());
                        }
                    }
                    listOfArticle.add(article);
                }
            }
        }

        catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
        return listOfArticle;
    }
    @Override
    public String toString() {
        String message = "id "+ getId() + "\n Title: "  + getTitle() + "\n Author "  + getAuthor() + "\n Summary "  + getSummary();
        return message;
    }

    public static Date toDate(String stringDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        return sdf.parse(stringDate);
    }

    public static void sortByDateOfPublication(LinkedList<Article> listOfArticle){
        listOfArticle.sort(new Comparator<>() {
            DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

            @Override
            public int compare(Article a1, Article a2) {
                try {
                    return df.parse(a1.getDateOfPublication()).compareTo(df.parse(a2.getDateOfPublication()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    public static void sortByDateOfUpdate(LinkedList<Article> listOfArticle){
        listOfArticle.sort(new Comparator<>() {
            DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

            @Override
            public int compare(Article a1, Article a2) {
                try {
                    return df.parse(a1.getDateOfUpdate()).compareTo(df.parse(a2.getDateOfUpdate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    public static Article getArticleByID(LinkedList<Article> listOfArticle, String id){
        for (Article article : listOfArticle) {
            if (article.getId().contains(id)) {
                return article;
            }
        }
        return null;
    }

    public static LinkedList<Article> getArticlesByAuthor(LinkedList<Article> listOfArticle, String author){
        LinkedList<Article> selectedAuthors = new LinkedList<>();
        for (Article article : listOfArticle) {
            if (article.getAuthor().contains(author)) {
                selectedAuthors.addLast(article);
            }
        }
        return selectedAuthors;
    }

    public static LinkedList<Article> getArticlesByTitle(LinkedList<Article> listOfArticle, String titleWord){
        LinkedList<Article> selectedTitle = new LinkedList<>();
        for (Article article : listOfArticle) {
            if (article.getTitle().toLowerCase().contains(titleWord.toLowerCase())) {
                selectedTitle.addLast(article);
            }
        }
        return selectedTitle;
    }
    public static LinkedList<Article> getArticlesByKeyword(LinkedList<Article> listOfArticle, String keyword){
        LinkedList<Article> selectedTitle = new LinkedList<>();
        for (Article article : listOfArticle) {
            if (article.getSummary().toLowerCase().contains(keyword.toLowerCase()) || article.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                selectedTitle.addLast(article);
            }
        }
        return selectedTitle;
    }
}
