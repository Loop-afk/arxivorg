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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static app.arxivorg.model.Article.*;
import static javax.print.attribute.standard.MediaSizeName.A;

public class Main {

    public static void main(String[] args) throws ParseException {
        LinkedList<Article> listOfArticle = readFile("test.atom");
        sortByDateOfPublication(listOfArticle);
        System.out.println(toDate(listOfArticle.get(2).getDateOfPublication()));
    }
}

