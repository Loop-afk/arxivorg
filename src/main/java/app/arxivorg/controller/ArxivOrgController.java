package app.arxivorg.controller;
import app.arxivorg.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;


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
        /*
            si je clique sur le bouton
            => alors je télécharge les articles (sous formes PDF)
        */
        //  1er test : je clique => j'affiche un msg combien de fois j'ai cliqué :
        downloadButton.setOnAction(event -> {
            nClicks++;
            System.out.println("Clicked " + nClicks + " times.");
        });

    }

}
