package app.arxivorg.controller;
import app.arxivorg.model.Article;
import app.arxivorg.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.TextFlow;
import org.w3c.dom.Text;

import javax.swing.text.html.ListView;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FavoritesController implements Initializable  {

    public ListView listView;
    @FXML
    public Button downloadButton;
    @FXML
    public Button deleteButton;
private List<Article> favorites=new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }






    /*public void displayArticles(){
        for(Article article: favorites){
            TextFlow flow = new TextFlow();
            flow.getChildren().addAll(ArxivOrgController.getStyleText(article));
            listView.getElement().add(flow);
        }
    }*/


    }








