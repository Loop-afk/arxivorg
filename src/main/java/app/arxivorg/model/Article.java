package app.arxivorg.model;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Article extends Authors {
    String id;
    String dateOfUpdate;
    String dateOfPublication;
    String title;
    Authors authors;
    String summary;
    String comment;
    List<String> categories;
    String linkOfArticle;
    String linkOfArticlePDF;

    private static List<Article> articles;

    public Article() {
        authors = new Authors();
    }

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

    public Authors getAuthor() {
        return authors;
    }

    public void setAuthor(Authors authors) {
        this.authors = authors;
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

    public List<String> getCategory() {
        return categories;
    }

    public void setCategory(List<String> category) {
        this.categories = category;
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
    public static LinkedList<Article> readFile(String file) {
        LinkedList<Article> listOfArticle = new LinkedList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String stringDate;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(file)));

            DocumentTraversal traversal = (DocumentTraversal) document;
            NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);

            for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {

                if (n.getNodeName().contentEquals("entry")) {
                    NodeList nodeList = n.getChildNodes();
                    Article article = new Article();
                    ArrayList<String> authors = new ArrayList<>();
                    ArrayList<String> categories = new ArrayList<>();

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        if (nodeList.item(i).getNodeName().contains("updated")) {
                            stringDate = nodeList.item(i).getTextContent();
                            stringDate = stringDate.replace("T", " ");
                            stringDate = stringDate.replace("Z", "");
                            article.setDateOfUpdate(stringDate);
                        }

                        if (nodeList.item(i).getNodeName().contains("published")) {
                            stringDate = nodeList.item(i).getTextContent();
                            stringDate = stringDate.replace("T", " ");
                            stringDate = stringDate.replace("Z", "");
                            article.setDateOfPublication(stringDate);
                        }

                        if (nodeList.item(i).getNodeName().contains("id")) {
                            article.setId(nodeList.item(i).getTextContent());
                        }

                        if (nodeList.item(i).getNodeName().contains("title")) {
                            article.setTitle(nodeList.item(i).getTextContent());
                        }

                        if (nodeList.item(i).getNodeName().contains("author")) {
                            authors.add(nodeList.item(i).getTextContent().trim().split("\n")[0]);
                            article.getAuthor().setData(authors);
                        }

                        if (nodeList.item(i).getNodeName().contains("summary")) {
                            //TODO:Essayer de mieux placer les retours ?? la ligne pour qu'ils correspondent ?? la limite de la fen??tre.
                            article.setSummary(nodeList.item(i).getTextContent().trim()/*.replace("\n", " ")*/);
                        }

                        if (nodeList.item(i).getNodeName().contains("arxiv:comment")) {
                            article.setComment(nodeList.item(i).getTextContent());
                        }

                        if (nodeList.item(i).getNodeName().contains("arxiv:primary_category") || nodeList.item(i).getNodeName().contains("category")) {
                            if (!categories.contains(nodeList.item(i).getAttributes().getNamedItem("term").getTextContent())) {
                                categories.add(nodeList.item(i).getAttributes().getNamedItem("term").getTextContent());
                                article.setCategory(categories);
                            }
                        }

                        if (nodeList.item(i).getNodeName().contains("link")) {
                            if (nodeList.item(i).getAttributes().getNamedItem("title") != null) {
                                article.setLinkOfArticlePDF(nodeList.item(i).getAttributes().getNamedItem("href").getTextContent().replace("http","https"));
                            } else
                                article.setLinkOfArticle(nodeList.item(i).getAttributes().getNamedItem("href").getTextContent().replace("http","https"));
                        }
                    }
                    listOfArticle.add(article);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return listOfArticle;
    }

    @Override
    public String toString() {
        return "id " + getId() + "\n Title: " + getTitle() + "\n Author: " + getAuthor();
    }


    public static Date toDate(String stringDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        return sdf.parse(stringDate);
    }

    public static void sortByDateOfPublication(LinkedList<Article> listOfArticle) {
        listOfArticle.sort(new Comparator<>() {
            DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

            @Override
            public int compare(Article a1, Article a2) {
                try {
                    return df.parse(a2.getDateOfPublication()).compareTo(df.parse(a1.getDateOfPublication()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    public static void sortByDateOfUpdate(LinkedList<Article> listOfArticle) {
        listOfArticle.sort(new Comparator<>() {
            DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

            @Override
            public int compare(Article a1, Article a2) {
                try {
                    return df.parse(a2.getDateOfUpdate()).compareTo(df.parse(a1.getDateOfUpdate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    public static List<Article> getArticles(){
        return articles;
    }


    public static Article getArticleByID(LinkedList<Article> listOfArticle, String id) {
        for (Article article : listOfArticle) {
            if (article.getId().contains(id)) {
                return article;
            }
        }
        return null;
    }

    public static Authors getAllAuthors(LinkedList<Article> listOfArticle) {
        Authors allAuthors = new Authors();

        for (int i = 0; i <listOfArticle.size();i++) {
            Article currentArticle = listOfArticle.get(i);
            for (int j = 0; j < currentArticle.getAuthor().getData().size(); j++){
                String currentAuthor = currentArticle.getAuthor().getData().get(j);

                if (j == currentArticle.getAuthor().getData().size()-1 && i != listOfArticle.size()-1 && !allAuthors.getData().contains(currentAuthor)){
                    allAuthors.getData().add(currentAuthor);
                }

                else if (!allAuthors.getData().contains(currentAuthor)) {
                    allAuthors.getData().add(currentAuthor);
                }
            }

        }
        return allAuthors;
    }

    public static List<String> getAllCategories(LinkedList<Article> listOfArticle) {
        List<String> allCategories = new ArrayList<>();
        for (Article article : listOfArticle) {
            for (String category : article.getCategory()) {
                if (!allCategories.contains(category)) {
                    allCategories.add(category);
                }
            }
        }
        return allCategories;
    }


    //Attention l'ID doit ??tre de forme "xxxx.xxxxx" .
    public static Article filteredByID(LinkedList<Article> listOfArticle, String id) {
        for (Article article : listOfArticle) {
            if (article.getId().contains(id)) {
                return article;
            }
        }
        return null;
    }

    public static String[] toArray(String authors, String delimiter) {
        return authors.split(delimiter);
    }

    public static LinkedList<Article> filteredByAuthors(LinkedList<Article> listOfArticle, String[] authors) {
        LinkedList<Article> filteredListByAuthors = new LinkedList<>();
        for (String author : authors) {
            author = author.trim();
            for (Article article : listOfArticle) {
                if (article.getAuthor().getData().contains(author) && !filteredListByAuthors.contains(article)) {
                    filteredListByAuthors.addLast(article);
                }
            }
        }
        return filteredListByAuthors;
    }

    /*TODO: Faire attention car l'API d'ArXiv est un peu bizarre, elle fait des recherches directement dans tous l'article.
        Par exemple si on recherche "proteins" on obtient un article qui ne contient pas le mot proteins dans son titre ou son
        r??sum?? mais seulement dans le texte de l'article. Donc mieux de chercher au singulier car plus g??n??ral.
     */
    public static LinkedList<Article> filteredByKeyword(LinkedList<Article> listOfArticle, String keyword) {
        LinkedList<Article> filteredListByKeyword = new LinkedList<>();
        for (Article article : listOfArticle) {
            if (article.getSummary().toLowerCase().contains(keyword.toLowerCase()) || article.getTitle().toLowerCase().contains(keyword.toLowerCase()) || article.getComment()!= null && article.getComment().toLowerCase().contains(keyword.toLowerCase())) {
                filteredListByKeyword.addLast(article);
            }
        }
        return filteredListByKeyword;
    }

    public static LinkedList<Article> filterByCategory(LinkedList<Article> listOfArticle, String categoryTag) {
        LinkedList<Article> filteredListByCategory = new LinkedList<>();
        for (Article article : listOfArticle) {
            for (String category : article.getCategory()) {
                if (category.equals(categoryTag)) {
                    filteredListByCategory.add(article);
                }
            }
        }
        return filteredListByCategory;
    }

    public static LinkedList<Article> filterByDateOfUpdate(LinkedList<Article> listOfArticle, Date date) throws ParseException {
        LinkedList<Article> filteredListByDateOfUpdate = new LinkedList<>();
        for (Article article : listOfArticle) {
            if (toDate(article.getDateOfUpdate()).after(date))
                filteredListByDateOfUpdate.add(article);
        }
        return filteredListByDateOfUpdate;
    }

    public static LinkedList<Article> filterByDateOfPublication(LinkedList<Article> listOfArticle, Date date) throws ParseException {
        LinkedList<Article> filteredListByDateOfPublication = new LinkedList<>();
        for (Article article : listOfArticle) {
            if (toDate(article.getDateOfPublication()).after(date))
                filteredListByDateOfPublication.add(article);
        }
        return filteredListByDateOfPublication;
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    //TODO: Faire de nouvelles fonctions ou compl??ter ces fonctions pour qu'elles puissent faire OR/AND/ANDNOT.
    public static String getArticlesFromArXivWithLimitedNumber(String search, int numberMaxOfArticles) throws Exception {
        String[] searchWords = toArray(search,",");
        StringBuilder URItoGet = new StringBuilder("http://export.arxiv.org/api/query?search_query=all:");

        for (int i = 0; i < searchWords.length; i++) {
            if (i == 0) {
                URItoGet.append(searchWords[i].trim().toLowerCase());
            } else {
                URItoGet.append("+OR+all:").append(searchWords[i].trim().toLowerCase());
            }
        }
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URItoGet.toString()+"&start=0&max_results=" + numberMaxOfArticles))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getArticlesFromArXiv(String search) throws Exception {
        String[] searchWords = toArray(search, ",");
        StringBuilder URItoGet = new StringBuilder("http://export.arxiv.org/api/query?search_query=all:");
        for (int i = 0; i < searchWords.length; i++) {
            if (i == 0) {
                URItoGet.append(searchWords[i].trim().toLowerCase());
            } else {
                URItoGet.append("+OR+all:").append(searchWords[i].trim().toLowerCase());
            }
        }

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URItoGet.toString()+"&start=0&max_results=100"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void Download(Article article, String path) throws IOException {
        URL url = new URL(article.getLinkOfArticlePDF());
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        String filename = article.getTitle().replaceAll("[:/\"\\n\\r<>?*|]"," ")+".pdf";
        FileOutputStream fos = new FileOutputStream(new File (path + File.separator + filename));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}