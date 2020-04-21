package app.arxivorg.controller;
import app.arxivorg.model.Article;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Set;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<String> listView;
    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    @FXML private Button downloadButton ;
    @FXML private TextField showDetailsField ;

    ObservableList<Article> names = FXCollections.observableArrayList(Article.infos);

    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
        setCbxCategories();
        downloadArticles();
       // showInfosArticles();
    }

    @FXML
    private void setShortListView(){
        shortListView.refresh();
        shortListView.getItems().addAll(names);
        shortListView.addEventFilter(MouseEvent.MOUSE_PRESSED,
                    mouseEvent ->
                            showDetailsField.setText("&"));
    }


//    @FXML
//    public void displaySelected(MouseEvent mouseEvent) {
//        int index = listView.getSelectionModel().getSelectedIndex();
//        Article article =  Article.getArticleByID().get(index);
//        showDetailsField.setText("Title: "+article.getTitle()+"\nAuteurs: "+ article.getAuthor()
//                +"\nDescription: \n"+article.getSummary()+"\nLien: "+article.getId());
//    }

    @FXML
    private void setCbxCategories(){
        cbxCategories.getItems().addAll(Article.getAllCategories(Article.infos));
    }

    @FXML
    private void downloadArticles (){

//            downloadButton.setOnAction(event -> {
//                System.out.println("la liste d article : " + names);
//            });

            downloadButton.setOnAction(event -> {
                try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://arxiv.org/pdf/2003.04748.pdf").openStream());
                     FileOutputStream fileOS = new FileOutputStream("C:\\Users\\chahi\\Downloads\\articles.pdf")) {
                    byte data[] = new byte[1024];
                    int byteContent;
                    while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                        fileOS.write(data, 0, byteContent);
                    }
                } catch (IOException e) {
                    // handles IO exceptions
                }

            });
    }
    // télécharger les catégories d'un fichier a un ressource
    private static Set<String> loadCategories(){
        Set<String> result= new HashSet<>();
        File file = new File("src/main/resources/categories.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String category = "";
            while ((category = bufferedReader.readLine()) != null){
                result.add(category);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

    private static void downloadArticleToPDF(Article article){
        try {
            String link1=article.getId().replace("abs","pdf");
            String link2=link1.replace("http", "https");
            URL url = new URL(link2);

            InputStream in = url.openStream();
            Path path1 = FileSystems.getDefault().getPath(System.getProperty("user.home"), "/Documents/", "arxivorg");
            Files.createDirectories(path1);
            String[] tab=article.getId().split("/");
            String fineName=tab[tab.length-1];
            Path path2= Paths.get(path1.toString().concat("/"+fineName+".pdf"));
            Files.copy(in, path2, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



























}
