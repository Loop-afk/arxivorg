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
import java.util.LinkedList;
import java.util.List;

public class Article {
    String id;
    String dateOfPublication;
    String title;
    List<String> author;
    String summary;
    String comment;
    String category;
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
        String testDate;

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
                        if(nodeList.item(i).getNodeName().contains("published")) {
                            testDate = nodeList.item(i).getTextContent();
                            testDate = testDate.substring(0,10);
                            article.setDateOfPublication(testDate);
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
}
