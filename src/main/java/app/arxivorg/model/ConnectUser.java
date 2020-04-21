package app.arxivorg.model;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ConnectUser {
    public static Connection connection;
    public static boolean isConnected;
    public static User user;


    public static void createTable() throws Exception{
        try{
            Connection connect = getConnection("Admin","forTestOnly");
            assert connect != null;
            PreparedStatement createTable = connect.prepareStatement("CREATE TABLE IF NOT EXISTS Users(ID int NOT NULL AUTO_INCREMENT, Username VARCHAR(255) NOT NULL, Password VARCHAR(255) NOT NULL, PRIMARY KEY(ID, Username)");
            createTable.executeUpdate();
        }

        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertTable(String username, String password) throws Exception{
        try{
            Connection connect = getConnection("Admin", "forTestOnly");
            assert connect != null;
            PreparedStatement insert = connect.prepareStatement("INSERT INTO Users(Username, Password) VALUES ('" + username + "', '" + password + "')");
            insert.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static Connection getConnection(String username, String password) throws Exception{
        try{
            String driver = "com.mysql.jdbc.Driver";
            String urlDB = "jdbc:mysql://localhost:3306/database";
            Class.forName(driver);

            Connection connect = DriverManager.getConnection(urlDB,username,password);
            return connect;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}