package app.arxivorg.controller;
import app.arxivorg.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<String> listView;
    @FXML private ListView<Article> shortListView;
    @FXML private ComboBox<String> cbxCategories;
    @FXML private Button downloadButton ;
    @FXML private TextArea showDetailsField ;
    @FXML private TextField searchByKeyWords;
    @FXML private TextField searchByAuthors;
    @FXML private CheckBox favoriteCheckBox;


    private LinkedList<Article> infos = new LinkedList<>(Article.readFile(Article.getArticlesFromArXivWithLimitedNumber("",100)));

    ObservableList<Article> names = FXCollections.observableArrayList(infos);

    public ArxivOrgController() throws Exception {
    }


    public void initialize(URL location, ResourceBundle resourceBundle) {
        setShortListView();
        setCbxCategories();
        searchByKeywords();
      //  downloadArticles();
    }

    @FXML
    private void setShortListView(){
        shortListView.refresh();
        shortListView.setItems(names);
        shortListView.setOnMouseClicked(
                (MouseEvent e) -> {
                    if (!shortListView.getSelectionModel().isEmpty())
                        showDetailsField.setText(shortListView.getSelectionModel().getSelectedItem().getSummary());
                });
    }

//    @FXML
////    public void displaySelected(MouseEvent mouseEvent) {
////        int index = listView.getSelectionModel().getSelectedIndex();
////        Article article = Article.getArticles().get(index);
////        showDetailsField.setText("Title: "+article.getTitle()+"\nAuteurs: "+ article.getAuthor()
////                +"\nDescription: \n"+article.getSummary()+"\nLien: "+article.getId());
////    }

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
                });
    }

    @FXML
    private void searchByAuthors(){
        searchByAuthors.setOnAction(
                (ActionEvent e) -> {
                    try {
                        String authors = searchByAuthors.getText();
                        LinkedList<Article> filtered = new LinkedList<>(Article.readFile(Article.getArticlesFromArXiv(authors)));
                     //   names = FXCollections.observableArrayList(Article.filteredByAuthors(filtered,authors));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                  //  shortListView.setItems(names);
                });
    }
/*
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

     télécharger les catégories d'un fichier a un ressource
    private static Set<String> loadCategories(){
        Set<String> result= new HashSet<>();
        File file = new File("src/main/resources/categories.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String category = "";
            while ((category = bufferedReader.readLine()) != null){
                result.add(category);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

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

