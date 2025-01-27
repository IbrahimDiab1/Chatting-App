package com.liqaa.controllers;

import com.liqaa.HelloApplication;
import com.liqaa.util.FilePaths;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import com.liqaa.util.SceneManager;

import java.io.IOException;

public class LoginController
{
    @FXML
    private TextField passwordTextField;
    @FXML
    private ImageView passwordIcon;
    @FXML
    private TextField adminIdTextField;
    @FXML
    private Button AdminLogInButton;

    @FXML
    public void handlePasswordVisability(Event event)
    {

    }

    @FXML
    public void handleAdminLogInButton(ActionEvent actionEvent)
    {
        try {
            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(HelloApplication.class.getResource(FilePaths.MANAGER_SCREEN_FXML))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
