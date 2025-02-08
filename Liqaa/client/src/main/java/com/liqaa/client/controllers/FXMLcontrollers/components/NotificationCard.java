package com.liqaa.client.controllers.FXMLcontrollers.components;

import com.liqaa.client.util.DateFormatter;
import com.liqaa.shared.models.entities.Notification;
import com.liqaa.shared.models.enums.NotificationType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NotificationCard { // I need sender photo & name + announcement title

    public static HBox createNotificationCard(Notification notification) {
        HBox card = new HBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-border-color: #E0E0E0;");

        HBox.setHgrow(card, Priority.ALWAYS);
        Label senderId = new Label();
        senderId.setText(String.valueOf(notification.getSenderId()));
        card.getChildren().add(senderId);

        Label annId = new Label();
        annId.setText(String.valueOf(notification.getAnnouncementId()));
        card.getChildren().add(annId);

        Label type = new Label();
        if(notification.getType().equals(NotificationType.REQUEST_ACCEPTED)){
            type.setText("Salma accepted your invitation request");
        }
        else if(notification.getType().equals(NotificationType.REQUEST_DECLINED)){
            type.setText("Salma declined your invitation request");
        }
        else if(notification.getType().equals(NotificationType.FRIEND_REQUEST)){
            type.setText("Salma sent you an invitation request");
        }
        else if(notification.getType().equals(NotificationType.ANNOUNCEMENT)){ // announcement title to be displayed
            type.setText("New feature alert!");
        }

        card.getChildren().add(type);

        Label sentAt = new Label();
        sentAt.setText(DateFormatter.formatDate(notification.getSentAt()));
        card.getChildren().add(sentAt);

//        ImageView profileImage = new ImageView(new Image(imageUrl));
//        profileImage.setFitWidth(40);
//        profileImage.setFitHeight(40);
//        profileImage.setClip(new Circle(20, 20, 20));


//        Label userLabel = new Label(userName);
//        userLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
//
//        Label messageLabel = new Label(message);
//        messageLabel.setStyle("-fx-font-size: 13px;");
//        messageLabel.setWrapText(true);
//
//        Label dateLabel = new Label(date);
//        dateLabel.setTextFill(Color.GRAY);
//        dateLabel.setStyle("-fx-font-size: 11px;");
//
//        // الحاوية النصية
//        VBox textContainer = new VBox(userLabel, messageLabel, dateLabel);
//        VBox.setVgrow(textContainer, Priority.ALWAYS);
//
//        // أزرار الإجراء (حذف أو قبول/رفض)
//        HBox actionsBox = new HBox(5);
//        actionsBox.setAlignment(Pos.CENTER_RIGHT);
//
//        if (hasActions) {
//            Button acceptButton = new Button("Accept");
//            acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-padding: 5 10;");
//
//            Button declineButton = new Button("Decline");
//            declineButton.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-padding: 5 10;");
//
//            actionsBox.getChildren().addAll(acceptButton, declineButton);
//        }
//
//        Button deleteButton = new Button("❌");
//        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 14px;");
//        actionsBox.getChildren().add(deleteButton);
//
//        // إضافة العناصر إلى HBox الرئيسي
//        card.getChildren().addAll(profileImage, textContainer, actionsBox);

        return card;
    }
}

