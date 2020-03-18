package app.arxivorg.model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class AtomReading extends Article {

    public static LinkedList<Article> readFile(String pathname){
        LinkedList<Article> listOfArticle = new LinkedList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String test = "";
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

                    for (int i = 0; i < nodeList.getLength(); i++){
                        if(nodeList.item(i).getNodeName().contains("published")) {
                            testDate = "Date : " + nodeList.item(i).getTextContent();
                            testDate = testDate.substring(0,17);
                            article.setDateOfPublication(testDate);
                        }

                        if(nodeList.item(i).getNodeName().contains("id")){
                            article.setId(nodeList.item(i).getTextContent());
                        }

                        if(nodeList.item(i).getNodeName().contains("title")){
                            article.setTitle(nodeList.item(i).getTextContent());
                        }

                        if (nodeList.item(i).getNodeName().contains("author")) {
                            List<String> authors = new LinkedList<>();
                            authors.add(nodeList.item(i).getTextContent());
                            /*NodeList nodeListAuthor = nodeList.item(i).getChildNodes();
                            for (int j = 0; j < nodeListAuthor.getLength(); j++) {

                            }*/
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
    return listOfArticle;
    }
}


