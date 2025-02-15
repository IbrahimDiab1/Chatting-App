package com.liqaa.server.controllers.FXMLcontrollers;

import com.liqaa.server.Main;
import com.liqaa.server.controllers.services.implementations.MessageServiceImpl;
import com.liqaa.server.util.FilePaths;
import com.liqaa.server.util.SceneManager;
import com.liqaa.shared.models.entities.Announcement;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AnnouncementsController {
    @javafx.fxml.FXML
    private TextField titleTextField;
    @javafx.fxml.FXML
    private TextField announcementTextField;
    @javafx.fxml.FXML
    private Button sendButton;

    @javafx.fxml.FXML
    public void logout(Event event) {
        try {
            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(Main.class.getResource(FilePaths.LOGIN_SCREEN_FXML))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void switchToStatistics(Event event)
    {
        try {
            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(Main.class.getResource(FilePaths.STATISTICS_SCREEN_FXML))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void switchToAnnouncements(Event event)
    {
        try {
            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(Main.class.getResource(FilePaths.ANNOUNCEMENTS_SCREEN_FXML))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void sendAnnouncement(Event actionEvent) {

        try {
            Announcement AnnouncementObject=new Announcement( titleTextField.getText(),announcementTextField.getText());
            MessageServiceImpl.getInstance().createAnnouncement(AnnouncementObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        titleTextField.clear();
        announcementTextField.clear();

    }

    @javafx.fxml.FXML
    public void switchToManager(Event event)
    {
        try {
            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(Main.class.getResource(FilePaths.MANAGER_SCREEN_FXML))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}