package com.liqaa.client.controllers.FXMLcontrollers.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;


public class NotificationCard1Controller implements Initializable {
    @FXML
    private Circle profilePhoto;

    @FXML
    private Label notificationTitle;

    @FXML
    private Label notificationDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void deleteAction(){
        System.out.println("delete icon is clicked");
    }
}
