package com.liqaa.server;

import com.liqaa.server.util.FilePaths;
import com.liqaa.server.util.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FilePaths.LOGIN_SCREEN_FXML));
//        Pane root = fxmlLoader.load();
//        Scene scene = new Scene(root, 890, 610);
//        SceneManager.initialize(stage);
//        stage.setTitle("Liqaa!");
//        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}