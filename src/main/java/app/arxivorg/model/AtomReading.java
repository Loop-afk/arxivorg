package app.arxivorg.model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

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

    public LinkedList<Article> readFile(String pathname){
        LinkedList<Article> listOfArticle = new LinkedList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String test;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse("test.atom");
            NodeList nList = document.getElementsByTagName("id");

            DocumentTraversal traversal = (DocumentTraversal) document;
            NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT,null,true);

            for(Node n = iterator.nextNode(); n != null; n = iterator.nextNode()){

                if(n.getNodeName().contentEquals("id")){
                test = n.getTextContent();
                System.out.println((test));
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


