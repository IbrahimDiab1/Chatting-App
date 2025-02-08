package com.liqaa.client.controllers.FXMLcontrollers.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationCard2Controller implements Initializable {
    @FXML
    private Circle profilePhoto;

    @FXML
    private Label notificationTitle;

    @FXML
    private Label notificationDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void acceptAction(){
        System.out.println("accept button is clicked");
    }

    public void declineAction(){
        System.out.println("decline button is clicked");
    }
}
