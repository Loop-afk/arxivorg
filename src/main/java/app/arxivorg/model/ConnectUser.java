
package app.arxivorg.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ConnectUser {
    public static JSONObject data = new JSONObject();


    //Fonction permettant de créer un fichier JSON qui contiendra tous les utilisateurs.
    public static void generateJsonFile() throws JSONException{
    User[] users = {new User("username", "password")};
    JSONObject json = new JSONObject();
    json.put("Admin", new JSONArray(users));

    //Création d'un nouveau fichier "Users".
    FileWriter fs = null;
        try {
            fs = new FileWriter("Users");
        } catch(IOException e) {
            System.err.println("Erreur lors de l'ouverture du fichier Users.json");
            System.err.println(e);
            System.exit(-1);
        }

        //Ecriture de l'objet json dans le nouveau fichier.
        try {
            json.write(fs, 3, 0);
            fs.flush();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier.");
            System.err.println(e);
            System.exit(-1);
        }

        //Fermeture du fichier.
        try {
            fs.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture du fichier.");
            System.err.println(e);
            System.exit(-1);
        }
    }

    public static boolean checkUser(User user){
        int result = 0;

        //Ouverture du fichier Users.json.
        FileInputStream fs = null;
        try {
            fs = new FileInputStream("Users.json");
        } catch(FileNotFoundException e) {
            System.err.println("Fichier Users.json introuvable");
            System.exit(-1);
        }

        //Ouvre un scanner pour lire le fichier Users.json.
        String json = "";
        Scanner scanner = new Scanner(fs);

        //Construit un string dont le contenu représente celui du fichier JSON.
        while(scanner.hasNext())
            json += scanner.nextLine();
        scanner.close();
        json = json.replaceAll("[\t ]", "");

        //Convertit le contenu du fichier on objet json.
        JSONObject tmp = new JSONObject(json);

        //Regarde s'il existe une clef dont le nom représente l'utilisateur et si les valeurs rattachées sont bien celles passée en arguments.
        if (tmp.get(user.getUsername()) != null) {
            User test = (User) tmp.get(user.getUsername());
            if (test.getUsername().equals(user.getUsername()) && test.getPassword().equals(user.getPassword())) {
                result = 1;
            }
        }

        try {
            fs.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture du fichier.");
            System.err.println(e);
            System.exit(-1);
        }

        return result == 1;
    }

    public void addUser(User user){
        if (checkUser(user)) {
            User[] newUser = {new User(user.getUsername(), user.getPassword())};
            JSONObject json = new JSONObject();
            json.put("" + user.getUsername() + "", new JSONArray(newUser));

            //Ouvre un lecteur de fichier.
            FileInputStream fs = null;
            try {
                fs = new FileInputStream("Users.json");
            } catch (FileNotFoundException e) {
                System.err.println("Fichier Users.json introuvable");
                System.exit(-1);
            }

            //Ouvre un scanner pour lire le fichier Users.json.
            StringBuilder stringJson = new StringBuilder();
            Scanner scanner = new Scanner(fs);

            //Construit un string dont le contenu représente celui du fichier JSON.
            while (scanner.hasNext()) {
                stringJson.append(scanner.nextLine());
                scanner.close();
            }
            stringJson = new StringBuilder(stringJson.toString().replaceAll("[\t ]", ""));
            String j = stringJson.toString();

        }
        else {System.out.println("Veuillez choisir un autre nom d'utilisateur.");}
    }

    public void addFavorite(Article article){

    }
}
