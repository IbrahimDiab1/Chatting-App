package com.liqaa.server.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;
    private final Stage stage;

    private SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static void initialize(Stage stage)
    {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
    }

    public static SceneManager getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException("SceneManager is not initialized. Call initialize(Stage stage) first.");
        }
        return instance;
    }

    public void switchScene(Scene newScene) {
        if (stage != null) {
            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setScene(newScene);
            stage.setWidth(width);
            stage.setHeight(height);
        } else {
            System.err.println("Stage not set. Cannot switch scene.");
        }
    }
}