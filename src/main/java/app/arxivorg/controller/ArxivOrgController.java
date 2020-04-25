package app.arxivorg.controller;
import app.arxivorg.ArxivOrg;
import app.arxivorg.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    @FXML private Button downloadButton ;
    @FXML private TextArea showDetailsField ;
    @FXML private TextField searchByKeyWords;
    @FXML private TextField searchByAuthors;
    @FXML private VBox vBoxInfos;
    @FXML private Button addFavoriteButton;

    private LinkedList<Article> infos = new LinkedList<>(Article.readFile(Article.getArticlesFromArXivWithLimitedNumber("",100)));

    ObservableList<Article> names = FXCollections.observableArrayList(infos);

    public ArxivOrgController() throws Exception {
    }

   // public static List<Text>getStyleText(Article article){
      //  List<Text> texts=new ArrayList<>();
   // }


    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
        setCbxCategories();
        searchByKeywords();
        searchByAuthors();
        downloadArticles();
    }
    @FXML
    private void setShortListView(){
        shortListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        shortListView.refresh();
        shortListView.setItems(names);
        shortListView.setOnMouseClicked(
                (MouseEvent e) -> {
                    if (!shortListView.getSelectionModel().isEmpty())
                        showDetailsField.setText(shortListView.getSelectionModel().getSelectedItem().getSummary());
                });
    }

    @FXML
    private void setCbxCategories() {
        try {
            Scanner scanner = new Scanner(new File("Categories.txt"));
            Map<String, String> categories = new LinkedHashMap<>();
            while (scanner.hasNextLine()) {
                String[] categorie = scanner.nextLine().split(":", 2);
                categories.put(categorie[0], categorie[1]);
            }
            for (String key : categories.keySet()) {
                cbxCategories.getItems().addAll(categories.get(key));
            }
            cbxCategories.setOnAction(
                    (ActionEvent e) -> {
                        for(Map.Entry<String, String> key : categories.entrySet()){
                            if(key.getValue().contains(cbxCategories.getSelectionModel().getSelectedItem()))
                                names = FXCollections.observableArrayList(Article.filterByCategory(infos, key.getKey()));
                        }
                        shortListView.setItems(names);
                   /* names.clear();
                    shortListView.getItems().addAll(Article.filterByCategory(infos, cbxCategories.getSelectionModel().getSelectedItem()));*/
                    });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchByKeywords(){
        searchByKeyWords.setOnAction(
                (ActionEvent e) -> {
                    try {
                        String keyword = searchByKeyWords.getText();
                        LinkedList<Article> filtered = new LinkedList<>(Article.readFile(Article.getArticlesFromArXiv(keyword)));
                        names = FXCollections.observableArrayList(Article.filteredByKeyword(filtered,keyword));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showDetailsField.setText("Mauvais parametres de recherche, il doit etre de la forme suivante (les espaces sont à respecter): mot-cle, mot-cle2, mot-cle3...");
                    }
                    shortListView.setItems(names);
                    searchByAuthors();

                });
    }

    @FXML
    private void searchByAuthors(){

        //Auto-Complétion des auteurs en fonction de la liste affichée
/*        System.out.println(!searchByKeyWords.getText().isEmpty());
        LinkedList<String> authorsFromList = new LinkedList<>();
        for(Article authors: shortListView.getItems()) {
            authorsFromList.addAll(Arrays.asList(authors.getAuthor().toString().split(", ")));
        }
        AutoCompletionBinding autoCompletionBinding = TextFields.bindAutoCompletion(searchByAuthors, authorsFromList);
        if(!searchByKeyWords.getText().isEmpty()) {
            searchByKeyWords.setOnKeyPressed(
                    (KeyEvent e) -> {
                        TextFields.bindAutoCompletion(searchByAuthors, authorsFromList);
                        autoCompletionBinding.dispose();
                    });
            autoCompletionBinding.dispose();
        }*/

        searchByAuthors.setOnAction(
                (ActionEvent e) -> {
                    try {
                        String authors = searchByAuthors.getText();
                        LinkedList<Article> filtered = new LinkedList<>(Article.getAllArticles(authors));
                        names = FXCollections.observableArrayList(filtered);
                    } catch (Exception ex) {

                        showDetailsField.setText("La recherche doit se faire de la maniere suivante (les espaces sont a respecter): 'Prenom Nom' \nou bien directement en seprant les noms par une virgule");

                    }
                    shortListView.setItems(names);
                });
    }
    @FXML
    private void downloadArticles(){
        downloadButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    DirectoryChooser fc = new DirectoryChooser();
                    fc.setInitialDirectory(new File(System.getProperty("user.home")));
                    fc.setTitle("Enregistrer le fichier");
                    File download = fc.showDialog(downloadButton.getScene().getWindow());
                    if (download != null) {
                        try {
                            for(Article articles: shortListView.getSelectionModel().getSelectedItems()){
                                new File(download.getPath());
                                Article.Download(articles, download.getAbsolutePath());
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
/*
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
        }*/


























}

