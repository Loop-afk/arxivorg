package app.arxivorg.model;

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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static javax.print.attribute.standard.MediaSizeName.A;

public class Main {

    public static void main(String[] args) {
        LinkedList<Article> listOfArticle = new LinkedList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String testID = "";
        String testDate;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse("test.atom");

            DocumentTraversal traversal = (DocumentTraversal) document;
            NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT,null,true);

            for(Node n = iterator.nextNode(); n != null; n = iterator.nextNode()){

                if(n.getNodeName().contentEquals("entry")){
                    NodeList nodeList = n.getChildNodes();
                    Article article = new Article();

                    for (int i = 0; i < nodeList.getLength(); i++){
                        if(nodeList.item(i).getNodeName().contains("published")) {
                            testDate = "Date : " + nodeList.item(i).getTextContent();
                            testDate = testDate.substring(0,17);
                            article.setDateOfPublication(testDate);
                            System.out.println(testDate);
                        }

                        if(nodeList.item(i).getNodeName().contains("id")){
                            testID = "ID : " + nodeList.item(i).getTextContent();
                            article.setId(testID);
                            System.out.println(testID);
                        }

                        if(nodeList.item(i).getNodeName().contains("title")){
                            article.setTitle(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("author")){
                            List<String> authors = new LinkedList<>();
                            authors.add(nodeList.item(i).getTextContent());
                            article.setAuthor(authors);
                        }

                        if(nodeList.item(i).getNodeName().contains("summary")){
                           article.setSummary(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("comment")){
                            article.setComment(nodeList.item(i).getTextContent());
                        }

                        /*if(nodeList.item(i).getNodeName().contains("category")){
                            article.setCategory(nodeList.item(i).getTextContent());
                        }*/

                        if(nodeList.item(i).getNodeName().contains("link href=")){
                            article.setLinkOfArticle(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("link")){
                            article.setLinkOfArticlePDF(nodeList.item(i).getNamespaceURI());
                        }
                    }
                    listOfArticle.add(article);
                }
            }
        }

        catch(ParserConfigurationException | SAXException | IOException e){
        // factory.newDocumentBuilder()
        e.printStackTrace();
        }
    System.out.println(testID);
    }
}

