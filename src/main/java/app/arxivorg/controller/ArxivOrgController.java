package app.arxivorg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    ObservableList<String> items = FXCollections.observableArrayList("aa","jjj","khlk");
    @FXML private Button helloWorldButton;
    @FXML private Button goodByeWorldButton;
    @FXML private Label label;
    @FXML private ListView<String> shortListView;

    //    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        shortListView.getItems().addAll(items);
    }

    @FXML
    private void displayHelloWorld() {
        label.setText("Hello World");
        helloWorldButton.setVisible(false);
        if (!goodByeWorldButton.isVisible())
            goodByeWorldButton.setVisible(true);
    }

    @FXML
    private void goodByeWorld() {
        label.setText("");
        goodByeWorldButton.setVisible(false);
        if (!helloWorldButton.isVisible())
            helloWorldButton.setVisible(true);
    }

    @FXML
    private void shortListView(){
       shortListView.getItems().addAll(items);
    }
}
