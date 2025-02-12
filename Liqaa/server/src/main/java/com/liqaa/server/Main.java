package com.liqaa.server;

import com.liqaa.server.controllers.reposotories.implementations.NotificationRepoImpl;
import com.liqaa.server.network.ServerConnection;
import com.liqaa.server.util.FilePaths;
import com.liqaa.server.util.SceneManager;
import com.liqaa.shared.models.entities.AnnouncementNotification;
import com.liqaa.shared.models.entities.Notification;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Main extends Application
{
    private static Scene scene;
    @Override
    public void start(Stage stage)
    {
        try {
            Image appIcon = new Image(getClass().getResourceAsStream("/com/liqaa/server/view/images/logoIcon.png"));
            stage.getIcons().add(appIcon);

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

    public static void main(String[] args) throws SQLException, RemoteException {
        startServer();
        launch(args);
    }

    public static void startServer() throws RemoteException, SQLException
    {
        ServerConnection serverConnection = ServerConnection.getInstance();
        serverConnection.start();
        System.out.println("Server started");

    }


}