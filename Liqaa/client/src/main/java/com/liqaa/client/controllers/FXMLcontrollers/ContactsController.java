package com.liqaa.client.controllers.FXMLcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.text.Text;


public class ContactsController implements Initializable {
    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private TableColumn<Contact, Contact> nameColumn;

    @FXML
    private TableColumn<Contact, String> phoneNumberColumn;

    @FXML
    private TableColumn<Contact, String> bioColumn;

    @FXML
    private TableColumn<Contact, String> statusColumn;

    @FXML
    private TableColumn<Contact, String> categoryColumn;

    @FXML
    private TableColumn<Contact, Contact> actionColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
        nameColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Contact, Contact> call(TableColumn<Contact, Contact> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Contact item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(5); // spacing between icon and text
                            ImageView imageView = new ImageView(item.getPhoto());
                            imageView.setFitHeight(25); // icon size
                            imageView.setFitWidth(25);
                            Circle circle = new Circle(4); // circle size
                            if(item.getName().equals("Lindsey Stroud") || item.getName().equals("Micheal Owen") || item.getName().equals("Mary Jane")) {
                                circle.setFill(Color.web("33EC23"));
                            }
                            else{
                                circle.setFill(Color.web("EC2323"));
                            }
                            StackPane photoWithStatus = new StackPane(imageView);
                            StackPane.setAlignment(circle, Pos.BOTTOM_RIGHT); // circle position
                            photoWithStatus.getChildren().add(circle);
                            Text nameText = new Text(item.getName());
                            hBox.getChildren().addAll(photoWithStatus, nameText);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        bioColumn.setCellValueFactory(new PropertyValueFactory<>("bio"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        actionColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Contact, Contact> call(TableColumn<Contact, Contact> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Contact item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(5); // spacing between icon and text
                            ImageView imageView = new ImageView(item.getBlock());
                            imageView.setFitHeight(20); // icon size
                            imageView.setFitWidth(20);
                            ImageView imageView2 = new ImageView(item.getEdit());
                            imageView2.setFitHeight(20); // icon size
                            imageView2.setFitWidth(20);
                            ImageView imageView3 = new ImageView(item.getDelete());
                            imageView3.setFitHeight(20); // icon size
                            imageView3.setFitWidth(20);
                            hBox.getChildren().addAll(imageView, imageView2, imageView3);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });

        ObservableList<Contact> contacts = FXCollections.observableArrayList(
                new Contact(new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/user1.png"))), "Lindsey Stroud", "+201000333511", "Muslim", "Available", "Friend", new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/block.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/edit.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/delete.png")))),
                new Contact(new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/user2.png"))), "Sarah brown", "+201033325420", "Feel free", "Busy", "Family", new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/block.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/edit.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/delete.png")))),
                new Contact(new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/user3.png"))), "Micheal Owen", "+201088865308", "Fueled by coffee & code", "Away", "Friend, Work", new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/block.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/edit.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/delete.png")))),
                new Contact(new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/user4.png"))), "Mary Jane", "+201077554274", "Just an ITIan :)", "Available", "Work", new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/block.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/edit.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/delete.png")))),
                new Contact(new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/user5.png"))), "Peter dodle", "+201045632103", "Sleeping...", "Away", "Work", new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/block.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/edit.png"))), new Image(String.valueOf(getClass().getResource("/com/liqaa/client/view/images/delete.png"))))
        );
        contactsTable.setItems(contacts);
    }

    public void profile_action(MouseEvent mouseEvent) {
        System.out.println("profile btn is clicked");
    }

    public void home_action(MouseEvent mouseEvent) {
        System.out.println("home btn is clicked");
    }

    public void notification_action(MouseEvent mouseEvent) {
        System.out.println("notification btn is clicked");
    }

    public void contact_action(MouseEvent mouseEvent) {
        System.out.println("contact btn is clicked");
    }

    public void chatbot_action(MouseEvent mouseEvent) {
        System.out.println("chatbot btn is clicked");
    }

    public void settings_action(MouseEvent mouseEvent) {
        System.out.println("settings btn is clicked");
    }

    public void logout_action(MouseEvent mouseEvent) {
        System.out.println("logout btn is clicked");
    }

    public void addContactAction(){
        System.out.println("add contact clicked");
    }
}
