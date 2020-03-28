package app.arxivorg.controller;

import app.arxivorg.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {


    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    ObservableList<Article> names = FXCollections.observableArrayList(Article.infos);



    //    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
        setCbxCategories();
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

}