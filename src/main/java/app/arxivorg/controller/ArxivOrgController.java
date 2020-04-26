package app.arxivorg.controller;
import app.arxivorg.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    @FXML private Button downloadButton;
    @FXML private TextArea showDetailsField;
    @FXML private TextField searchByKeyWords;
    @FXML private TextField searchByAuthors;
    @FXML private VBox vBoxInfos;
    @FXML private Button addFavoriteButton;
    @FXML private Button deleteFavoriteButton;
    @FXML private Label lblList;

    private LinkedList<Article> infos = new LinkedList<>(Article.readFile(Article.getArticlesFromArXivWithLimitedNumber("", 100)));

    ObservableList<Article> names = FXCollections.observableArrayList(infos);

    private LinkedList<Article> favoritesArticles = new LinkedList<>();


    public ArxivOrgController() throws Exception {
    }


    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
        setCbxCategories();
        searchByKeywords();
        searchByAuthors();
        downloadArticles();
        favoriteManager();
    }

    @FXML
    private void setShortListView() {
        //Le plus simple était ici à chaque fois qu'on rafraîchit la liste de remodier les labels et les boutons de favoriteManager() afin de permettre
        // à l'utilisateur de pouvoir ajouter des articles à ses favoris avec sa nouvelle recherche
        lblList.setText("Liste d'articles");
        addFavoriteButton.setVisible(true);
        deleteFavoriteButton.setVisible(false);

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
                        for (Map.Entry<String, String> key : categories.entrySet()) {
                            if (key.getValue().contains(cbxCategories.getSelectionModel().getSelectedItem()))
                                names = FXCollections.observableArrayList(Article.filterByCategory(infos, key.getKey()));
                        }
                        setShortListView();
                   /* names.clear();
                    shortListView.getItems().addAll(Article.filterByCategory(infos, cbxCategories.getSelectionModel().getSelectedItem()));*/
                    });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchByKeywords() {
        searchByKeyWords.setOnAction(
                (ActionEvent e) -> {
                    try {
                        String keyword = searchByKeyWords.getText();
                        LinkedList<Article> filtered = new LinkedList<>(Article.readFile(Article.getArticlesFromArXiv(keyword)));
                        names = FXCollections.observableArrayList(Article.filteredByKeyword(filtered, keyword));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showDetailsField.setText("Mauvais parametres de recherche, il doit etre de la forme suivante (les espaces sont à respecter): mot-cle, mot-cle2, mot-cle3...");
                    }
                    //shortListView.setItems(names);
                    setShortListView();
                    searchByAuthors();

                });
    }

    @FXML
    private void searchByAuthors() {

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

                        showDetailsField.setText("La recherche doit se faire de la maniere suivante (les espaces sont a respecter): 'Prenom Nom' ");

                    }
                    setShortListView();
                });
    }


    @FXML
    private void downloadArticles() {
        downloadButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    DirectoryChooser fc = new DirectoryChooser();
                    fc.setInitialDirectory(new File(System.getProperty("user.home")));
                    fc.setTitle("Enregistrer le fichier");
                    File download = fc.showDialog(downloadButton.getScene().getWindow());
                    if (download != null) {
                        try {
                            for (Article articles : shortListView.getSelectionModel().getSelectedItems()) {
                                new File(download.getPath());
                                Article.Download(articles, download.getAbsolutePath());
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    @FXML
    private void favoriteManager() {
        // Fonction qui gère l'ajout et la suppression d'article dans les favoris
        addFavoriteButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    if (!shortListView.getSelectionModel().getSelectedItems().isEmpty()) {
                        //for(int i = 0; i<= shortListView.getSelectionModel().getSelectedItems().size(); i++)
                        for(Article favoriteArticle : shortListView.getSelectionModel().getSelectedItems()){
                            if (!favoritesArticles.toString().contains(favoriteArticle.toString())) {
                                favoritesArticles.add(favoriteArticle);
                            System.out.println(favoritesArticles.toString());
                            }
                            else
                            showDetailsField.setText("Article deja present dans la liste des favoris");
                        }
                    }

                    if (favoritesArticles.isEmpty()) {
                        showDetailsField.setText("Ajoutez des articles a vos favoris dans un premier temps");
                    }
                    if (!favoritesArticles.isEmpty()){
                        // Ici je ne rappelle pas la fonction setShortListView car elle me modifierai les boutons + labels que j'ai mis en place plus haut
                        names = FXCollections.observableArrayList(favoritesArticles);
                        shortListView.setItems(names);
                        lblList.setText("Liste d'articles Favoris");
                        addFavoriteButton.setVisible(false);
                        deleteFavoriteButton.setVisible(true);
                    }
                    });


        deleteFavoriteButton.setOnAction(
                (ActionEvent e) -> {
                    if (!shortListView.getSelectionModel().getSelectedItems().isEmpty() && favoritesArticles != null) {
                        favoritesArticles.removeIf(deleteFromFavorite -> shortListView.getSelectionModel().getSelectedItems().contains(deleteFromFavorite));
                    }
                    names = FXCollections.observableArrayList(favoritesArticles);
                    shortListView.setItems(names);
                });
    }

}

