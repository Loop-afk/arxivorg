package app.arxivorg.controller;

import app.arxivorg.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<Article> shortListView;
    ObservableList<Article> names = FXCollections.observableArrayList(Article.infos);

    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
    }

    @FXML
    private void setShortListView(){
        shortListView.refresh();
        shortListView.getItems().addAll(names);
    }
    //show all catégories
   // private void showAllCategories(){
    //        Set<String> categories = new HashSet<>();
    //        for(Categorie var : managerArticle.getCategories()){
    //            categories.add(var.getName());
    //        }
    //        categorieComboBox.getItems().addAll(categories);
    //    }

    //  public void showAllPeriod(){
    //     this.periodComboBox.getItems().addAll(managerArticle.getPeriods());
    //    }
    // @FXML
    //    public void displaySelected(MouseEvent mouseEvent) {
    //        int index = listView.getSelectionModel().getSelectedIndex();
    //        Article article = getArticles().get(index);
    //        infosTextArea.setText("Title: "+article.getTitle()+"\nAuteurs: "+article.getArticleAuthors()
    //        +"\nDescription: \n"+article.getSummary()+"\nLien: "+article.getId());
    //    }






}