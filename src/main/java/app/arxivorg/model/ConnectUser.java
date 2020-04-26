package app.arxivorg.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConnectUser {
/*
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

    //Fonction qui permet de vérifier si un utilisateur existe dans le fichier json et le retourne le cas échéant.
    public static User checkUser(User user){
        User result = null;
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
                result = test;
            }
        }

        //Fermeture du fichier.
        try {
            fs.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture du fichier.");
            System.err.println(e);
            System.exit(-1);
        }

        return result;
    }

    //Permet d'ajouter un nouvel utilisateur si ce dernier n'existe pas déjà dans le fichier json.
    public static void addUser(User user) throws IOException {
        if (checkUser(user) == null) {
            //Permet de créer un writer qui va être capable d'ajouter des lignes dans un fichier.
            FileWriter writer = new FileWriter("Users.json", StandardCharsets.UTF_8);

            //Prépare le nouvel utilisateur devant être ajouté.
            User[] newUser = {new User(user.getUsername(), user.getPassword())};
            JSONObject json = new JSONObject();
            json.put("" + user.getUsername() + "", new JSONArray(newUser));

            //Permet d'écrire à la fin du fichier le nouvel utilisateur.
            writer.append(json.toString());
        }
        else {System.out.println("Veuillez choisir un autre nom d'utilisateur.");}
    }

    //Permet en cliquant sur un bouton "Ajouter" lié à un article de rajouter un article à la liste des favoris.
    public void addFavorite(Article article, User user) throws IOException{
            //Permet de créer un writer qui va être capable d'ajouter des lignes dans un fichier.
            FileWriter writer = new FileWriter("Users.json", StandardCharsets.UTF_8);

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

            //Transforme ce string en objet json pour avoir accès aux méthodes de recherche de contenu de la classe.
            JSONObject json = new JSONObject(j);

            //Ajout de l'article dans les favoris du bon utilisateur.
            User toChange = (User) json.get(user.getUsername());
            toChange.addArticle(article);

            //Suppression des données anciennes et ajout des nouvelles avec le nouvel article.
            json.remove(user.getUsername());
            json.put("" + user.getUsername() + "", new JSONArray(toChange));

            //Réécriture du fichier avec le changement.
            writer.write(json.toString());
    }

    //Permet en cliquant sur un bouton "Supprimer" lié à un article d'enlever un article de la liste des favoris.
    public void deleteFavorite(Article article, User user) throws IOException{
        //Permet de créer un writer qui va être capable d'ajouter des lignes dans un fichier.
        FileWriter writer = new FileWriter("Users.json", StandardCharsets.UTF_8);

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

        //Transforme ce string en objet json pour avoir accès aux méthodes de recherche de contenu de la classe.
        JSONObject json = new JSONObject(j);

        //Suppression de l'article dans les favoris du bon utilisateur.
        User toChange = (User) json.get(user.getUsername());
        toChange.removeArticle(article);

        //Suppression des données anciennes et ajout des nouvelles avec le nouvel article.
        json.remove(user.getUsername());
        json.put("" + user.getUsername() + "", new JSONArray(toChange));

        //Réécriture du fichier avec le changement.
        writer.write(json.toString());
       } 
 */

}