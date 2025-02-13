package com.liqaa.client;

import com.liqaa.client.controllers.services.interfaces.CurrentUser;
import com.liqaa.client.network.ServerConnection;
import com.liqaa.client.util.SceneManager;
import com.liqaa.shared.network.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        Image appIcon = new Image(getClass().getResourceAsStream("/com/liqaa/client/view/images/logoIcon.png"));
        stage.getIcons().add(appIcon);

        SceneManager.initialize(stage);
        SceneManager.getInstance().showSignInScene();

        stage.setTitle("Liqaa");
        stage.show();

        testServerConnection();
    }

    private void testServerConnection() throws RemoteException
    {
        Server server = ServerConnection.getServer();
        if (server != null) {
            String message = server.ping();
            System.out.println("Connected to server successfully. Server responded: " + message);
        } else {
            System.err.println("Failed to connect to server.");
        }
    }

    public static void main(String[] args) throws SQLException, RemoteException
    {
        System.out.println();
        launch();
    }
}
