package com.liqaa.client.controllers.FXMLcontrollers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SettingController {

    @FXML
    private ComboBox<String> AnnouncementCombo;

    @FXML
    private ComboBox<String> FriendRequestCombo;

    @FXML
    private ComboBox<String> MsgCombo;

    @FXML
    private ComboBox<String> RememberCombo;

    @FXML
    private ComboBox<String> RequestResponseCombo;

    @FXML
    private ImageView closeBtn;

    @FXML
    private Button deleteButton;

    @FXML
    void closeAction(MouseEvent event) {
        // Close the current stage/window
        closeBtn.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        // Set ComboBox values
        String[] options = {"Enable", "Disable"};

        AnnouncementCombo.getItems().addAll(options);
        FriendRequestCombo.getItems().addAll(options);
        MsgCombo.getItems().addAll(options);
        RememberCombo.getItems().addAll(options);
        RequestResponseCombo.getItems().addAll(options);

        // Set default value to "Enable"
        AnnouncementCombo.setValue("Enable");
        FriendRequestCombo.setValue("Enable");
        MsgCombo.setValue("Enable");
        RememberCombo.setValue("Enable");
        RequestResponseCombo.setValue("Enable");

        // Assign ComboBox values to string variables
        String announcementValue = AnnouncementCombo.getValue();
        String friendRequestValue = FriendRequestCombo.getValue();
        String msgValue = MsgCombo.getValue();
        String rememberValue = RememberCombo.getValue();
        String requestResponseValue = RequestResponseCombo.getValue();
    }

    @FXML
    void deleteAction(ActionEvent event) {

//
    }
}
