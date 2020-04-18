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
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    @FXML private Button downloadButton ;

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
        shortListView.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> System.out.println(names));
    }

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

}
