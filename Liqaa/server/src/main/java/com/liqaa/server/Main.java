package com.liqaa.server;

import com.liqaa.server.network.ServerConnection;
import com.liqaa.server.util.FilePaths;
import com.liqaa.server.util.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Main extends Application

{
    private static Scene scene;
    @Override
    public void start(Stage stage)  {
       /* scene = new Scene(loadFXML("/com/liqaa/server/view/fxml/announcements"), 1024, 768);
        stage.setScene(scene);
        stage.show();*/
        try {
            SceneManager.initialize(stage);
            Parent root = FXMLLoader.load(Main.class.getResource(FilePaths.LOGIN_SCREEN_FXML));
            Scene loginScene = new Scene(root, 1024, 768);
            SceneManager.getInstance().switchScene(loginScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        //startServer();
       Application.launch();

    }
    /*
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void startServer() throws RemoteException, SQLException
    {
        ServerConnection serverConnection = ServerConnection.getInstance();
        serverConnection.start();
        System.out.println("Server started");
    }*/

}