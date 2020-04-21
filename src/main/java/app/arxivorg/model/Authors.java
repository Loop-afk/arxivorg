package app.arxivorg.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Authors {
    private ArrayList<String> data;

    public Authors() {
        data = new ArrayList<>();
    }

    public Authors(List<String> input) {
        this.data = new ArrayList<>(input);
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public String toString() {
        return String.join(", ", data);
    }

    public static String reverseName(String author){
        String[] tempo = Article.toArray(author," ");
        return tempo[1]+ "_" +tempo[0];
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static LinkedList<Article> getAllArticles(String author) throws IOException, InterruptedException {

            String URItoGet = "http://export.arxiv.org/api/query?search_query=au:"+Authors.reverseName(author)+"&start=0&max_results=1000";

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URItoGet))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Article.readFile(response.body());
    }
}
