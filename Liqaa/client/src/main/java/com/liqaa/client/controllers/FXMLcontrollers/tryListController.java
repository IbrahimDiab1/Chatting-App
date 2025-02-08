package com.liqaa.client.controllers.FXMLcontrollers;

import com.liqaa.client.controllers.services.implementations.TryServiceImpl;
import com.liqaa.shared.models.entities.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class tryListController implements Initializable {
    @FXML
    private ListView<Notification> notificationsList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Notification> notifications = null;
        try {
            notifications = FXCollections.observableArrayList(TryServiceImpl.getInstance().getNotifications(3));
            notificationsList.setItems(notifications);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
