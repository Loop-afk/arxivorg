package app.arxivorg.controller;
import app.arxivorg.model.Article;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.util.Duration;


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    @FXML private Button downloadButton ;
    private static int nClicks = 0;

    ObservableList<Article> names = FXCollections.observableArrayList(Article.infos);

    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
        setCbxCategories();
        downloadArticles();
    }

    @FXML
    private void setShortListView(){
        shortListView.refresh();
        shortListView.getItems().addAll(names);
    }

    @FXML
    private void setCbxCategories(){
        cbxCategories.getItems().addAll(Article.getAllCategories(Article.infos));
    }

    @FXML
    private void downloadArticles (){

        // 2m test : je clique => j'affiche la liste d'articles (que par la suite on va la télécharger)
//            downloadButton.setOnAction(event -> {
//                System.out.println("la liste d article : " + Article.infos);
//            });

        // 3m test : je télécharge la liste

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

}
