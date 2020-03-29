package app.arxivorg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.Optional;


public class ArxivOrg extends Application {

    @FXML
    private Button downloadButton ;

    // Afficher la boite de dialogue de téléchargement
    private void showDialogueWin (){
        String [] choices = {"Article sélectionné", "Tout les articles"};
        ChoiceDialog<String> cDial = new ChoiceDialog<>(choices[0], choices);
        cDial.setTitle("Téléchargement");
        cDial.setHeaderText("Sélectionner votre choix :");
        cDial.setContentText("Choix : ");

        Optional<String> selection = cDial.showAndWait();
        selection.ifPresent(str -> System.out.println("Selection" + str));
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/app/arxivorg/view/Main.fxml"));
        primaryStage.setTitle("Main Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDialogueWin();
            }
        });
    }

    public static void main(String[] args) { launch(args); }
}
