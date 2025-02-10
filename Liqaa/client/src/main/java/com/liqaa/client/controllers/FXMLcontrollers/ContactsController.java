package com.liqaa.client.controllers.FXMLcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Dialog;



public class ContactsController implements Initializable {
    @FXML
    private ListView<Contact> contactsList;

    @FXML
    private TextField searchField; // تأكد من وجود fx:id في الـ FXML

    // القائمة الأصلية للجهات الاتصال
    private ObservableList<Contact> originalContactsList = FXCollections.observableArrayList();

    // القائمة المفلترة
    private FilteredList<Contact> filteredContactsList = new FilteredList<>(originalContactsList, p -> true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupListView();
        populateContacts();

        // ربط القائمة المفلترة بالـ ListView
        contactsList.setItems(filteredContactsList);

        // إضافة Listener على TextField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredContactsList.setPredicate(contact -> {
                // إذا كان النص المكتوب فارغ، عرض كل الجهات الاتصال
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // تحويل النص المكتوب والنص في القائمة إلى حروف صغيرة (Case Insensitive)
                String lowerCaseFilter = newValue.toLowerCase();

                // التحقق من وجود النص في الاسم أو الرقم
                if (contact.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // عرض الجهة الاتصال إذا كانت تطابق البحث
                } else if (contact.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // عرض الجهة الاتصال إذا كانت تطابق البحث
                }

                return false; // إخفاء الجهة الاتصال إذا لم تكن تطابق البحث
            });
        });
    }
    // Method to set the logo for a dialog


    // Method to set the logo for a dialog with a specific color
    private void setDialogLogo(Dialog<?> dialog, double hue, double saturation, double brightness, double contrast) {
        ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/com/liqaa/client/view/images/logo.png")));
        logoView.setFitHeight(40); // Adjust size as needed
        logoView.setFitWidth(40); // Adjust size as needed

        // Apply color adjustment
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);       // تغيير درجة اللون (-1 إلى 1)
        colorAdjust.setSaturation(saturation); // تغيير التشبع (-1 إلى 1)
        colorAdjust.setBrightness(brightness); // تغيير السطوع (-1 إلى 1)
        colorAdjust.setContrast(contrast);     // تغيير التباين (-1 إلى 1)

        logoView.setEffect(colorAdjust); // تطبيق التأثير على الصورة
        dialog.setGraphic(logoView); // Set the logo as the graphic of the dialog
    }
    private void setupListView() {
        contactsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Contact item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(createContactCell(item));
                        }
                    }
                };
            }
        });
    }

    private HBox createContactCell(Contact item) {
        HBox hBox = new HBox(10); // تقليل المسافة بين العناصر
        hBox.setAlignment(Pos.CENTER_LEFT);

        // صورة المستخدم مع الحالة
        ImageView imageView = new ImageView(item.getPhoto());
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        Circle circle = new Circle(4);
        circle.setFill(item.getStatus().equals("Available") ? Color.GREEN : Color.RED);
        StackPane photoWithStatus = new StackPane(imageView, circle);
        StackPane.setAlignment(circle, Pos.BOTTOM_RIGHT);

        // النصوص
        Text nameText = new Text(item.getName());
        nameText.setWrappingWidth(100); // تحديد عرض ثابت للاسم

        Text phoneText = new Text(item.getPhoneNumber());
        phoneText.setWrappingWidth(120); // تحديد عرض ثابت للرقم

        Text bioText = new Text(item.getBio());
        bioText.setWrappingWidth(150); // تحديد عرض ثابت للـ Bio

        Text statusText = new Text(item.getStatus());
        statusText.setWrappingWidth(80); // تحديد عرض ثابت للحالة

        Text categoryText = new Text(item.getCategory());
        categoryText.setWrappingWidth(100); // تحديد عرض ثابت للفئة

        // الأيقونات
        ImageView blockIcon = new ImageView(item.getBlock());
        blockIcon.setFitHeight(20);
        blockIcon.setFitWidth(20);

        ImageView editIcon = new ImageView(item.getEdit());
        editIcon.setFitHeight(20);
        editIcon.setFitWidth(20);

        ImageView deleteIcon = new ImageView(item.getDelete());
        deleteIcon.setFitHeight(20);
        deleteIcon.setFitWidth(20);

        // إضافة العناصر إلى الـ HBox
        hBox.getChildren().addAll(
                photoWithStatus, nameText, phoneText, bioText, statusText, categoryText, blockIcon, editIcon, deleteIcon
        );

        return hBox;
    }

    private void populateContacts() {
        originalContactsList.addAll(
                new Contact(new Image(getClass().getResource("/com/liqaa/client/view/images/user1.png").toString()), "Lindsey Stroud", "+201000333511", "Muslim", "Available", "Friend", new Image(getClass().getResource("/com/liqaa/client/view/images/block.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/edit.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/delete.png").toString())),
                new Contact(new Image(getClass().getResource("/com/liqaa/client/view/images/user2.png").toString()), "Sarah brown", "+201033325420", "Feel free", "Busy", "Family", new Image(getClass().getResource("/com/liqaa/client/view/images/block.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/edit.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/delete.png").toString())),
                new Contact(new Image(getClass().getResource("/com/liqaa/client/view/images/user3.png").toString()), "Micheal Owen", "+201088865308", "Fueled by coffee & code", "Away", "Friend, Work", new Image(getClass().getResource("/com/liqaa/client/view/images/block.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/edit.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/delete.png").toString())),
                new Contact(new Image(getClass().getResource("/com/liqaa/client/view/images/user4.png").toString()), "Mary Jane", "+201077554274", "Just an ITIan :)", "Available", "Work", new Image(getClass().getResource("/com/liqaa/client/view/images/block.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/edit.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/delete.png").toString())),
                new Contact(new Image(getClass().getResource("/com/liqaa/client/view/images/user5.png").toString()), "Peter dodle", "+201045632103", "Sleeping...", "Away", "Work", new Image(getClass().getResource("/com/liqaa/client/view/images/block.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/edit.png").toString()), new Image(getClass().getResource("/com/liqaa/client/view/images/delete.png").toString()))
        );
    }

    // Event handlers for the buttons
    @FXML
    private void profile_action(MouseEvent event) {
        System.out.println("Profile button clicked!");
    }

    @FXML
    private void home_action(MouseEvent event) {
        System.out.println("Home button clicked!");
    }

    @FXML
    private void notification_action(MouseEvent event) {
        System.out.println("Notification button clicked!");
    }

    @FXML
    private void contact_action(MouseEvent event) {
        System.out.println("Contact button clicked!");
    }

    @FXML
    private void chatbot_action(MouseEvent event) {
        System.out.println("Chatbot button clicked!");
    }

    @FXML
    private void settings_action(MouseEvent event) {
        System.out.println("Settings button clicked!");
    }

    @FXML
    private void logout_action(MouseEvent event) {
        System.out.println("Logout button clicked!");
    }

    @FXML
    private void addContactAction() {
        try {
            // Create the dialog
            Dialog<Contact> dialog = new Dialog<>();
            dialog.setTitle("Add Contact");
            dialog.setHeaderText("Enter the contact details:");
            setDialogLogo(dialog, 0.1, 0.3, 0.50, 0.0);

            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            // Apply custom styles to the dialog
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/liqaa/client/view/styles/style1.css").toExternalForm()
            );

            // Create the fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // Phone Field
            TextField phoneField = new TextField();
            phoneField.setPromptText("Phone Number");

            // Add the phone field to the grid
            grid.add(new Label("Phone:"), 0, 0);
            grid.add(phoneField, 1, 0);

            // Set the grid as the dialog content
            dialog.getDialogPane().setContent(grid);

            // Convert the result to a Contact object
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    return new Contact(
                            new Image(getClass().getResource("/com/liqaa/client/view/images/user1.png").toString()),
                            "New Contact", // Default name
                            phoneField.getText(), // Phone number
                            "", // Bio
                            "Available", // Status
                            "", // Category
                            new Image(getClass().getResource("/com/liqaa/client/view/images/block.png").toString()),
                            new Image(getClass().getResource("/com/liqaa/client/view/images/edit.png").toString()),
                            new Image(getClass().getResource("/com/liqaa/client/view/images/delete.png").toString())
                    );
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<Contact> result = dialog.showAndWait();
            result.ifPresent(contact -> {
                originalContactsList.add(contact); // Add the new contact to the original list
                System.out.println("New Contact: " + contact.getName() + " - " + contact.getPhoneNumber());
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in addContactAction: " + e.getMessage());
        }
    }

    @FXML
    private void addGroupAction() {
        try {
            // Create the dialog
            Dialog<Map<String, Object>> dialog = new Dialog<>();
            dialog.setTitle("Add Group");
            dialog.setHeaderText("Create a new group");
            dialog.setGraphic(null); // Remove the question mark icon

            setDialogLogo(dialog, 0.1, 0.3, 0.50, 0.0);

            ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);

            // Apply custom styles to the dialog
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/liqaa/client/view/styles/style1.css").toExternalForm()
            );

            // Create the layout
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // Group Image with Label
            HBox groupImageBox = new HBox(10);
            groupImageBox.setAlignment(Pos.CENTER_LEFT);

            ImageView groupImageView = new ImageView(new Image(getClass().getResource("/com/liqaa/client/view/images/defaultGroupProfile.png").toString()));
            groupImageView.setFitHeight(100);
            groupImageView.setFitWidth(100);
            groupImageView.setPreserveRatio(true);
            groupImageView.setCursor(Cursor.HAND);

            // حواف دائرية للصورة
            Circle clip = new Circle(50, 50, 50); // Circle(centerX, centerY, radius)
            groupImageView.setClip(clip);

            // إضافة ظل خفيف
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(10); // حجم الظل
            dropShadow.setOffsetX(0); // إزاحة أفقية
            dropShadow.setOffsetY(0); // إزاحة رأسية
            dropShadow.setColor(Color.color(0, 0, 0, 0.3)); // لون الظل (أسود مع شفافية)
            groupImageView.setEffect(dropShadow);

            Label chooseProfileLabel = new Label("Choose Group Profile");
            chooseProfileLabel.setStyle("-fx-text-fill: #384E6A; -fx-font-size: 14px;");
            chooseProfileLabel.setCursor(Cursor.HAND); // تغيير شكل المؤشر إلى يد عند التمرير فوق النص

            // Event handler لفتح FileChooser عند النقر على الصورة أو النص
            EventHandler<MouseEvent> openFileChooserHandler = event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Group Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
                );
                File selectedFile = fileChooser.showOpenDialog(dialog.getDialogPane().getScene().getWindow());
                if (selectedFile != null) {
                    groupImageView.setImage(new Image(selectedFile.toURI().toString()));
                }
            };

            // إضافة معالج الحدث للصورة والنص
            groupImageView.setOnMouseClicked(openFileChooserHandler);
            chooseProfileLabel.setOnMouseClicked(openFileChooserHandler);

            groupImageBox.getChildren().addAll(groupImageView, chooseProfileLabel);

            // Name Field
            TextField nameField = new TextField();
            nameField.setPromptText("Group Name");

            // Description Field
            TextField descriptionField = new TextField();
            descriptionField.setPromptText("Group Description");

            // Choose Contacts Button
            Button chooseContactsButton = new Button("Choose Contacts");
            chooseContactsButton.setStyle("-fx-background-color: #384E6A; -fx-text-fill: white; -fx-font-weight: bold;");

            // List to store selected contacts
            ObservableList<Contact> selectedContacts = FXCollections.observableArrayList();

            chooseContactsButton.setOnAction(event -> {
                // Open a dialog to select contacts
                Dialog<Void> contactsDialog = new Dialog<>();
                contactsDialog.setTitle("Choose Contacts");
                contactsDialog.setHeaderText("Select contacts to add to the group");
                contactsDialog.setGraphic(null); // Remove the question mark icon

                // Apply custom styles to the dialog
                contactsDialog.getDialogPane().getStylesheets().add(
                        getClass().getResource("/com/liqaa/client/view/styles/style1.css").toExternalForm()
                );

                // Set button types (Add and Close)
                ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
                ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
                contactsDialog.getDialogPane().getButtonTypes().addAll(addButton, closeButton);

                // Create a ListView with custom cells
                ListView<Contact> contactsListView = new ListView<>();
                contactsListView.setItems(contactsList.getItems()); // Use the existing contacts list

                // Set custom cell factory to display contacts with images and checkboxes
                contactsListView.setCellFactory(param -> new ListCell<>() {
                    private final CheckBox checkBox = new CheckBox();
                    private final HBox hBox = new HBox(10);
                    private final ImageView imageView = new ImageView();
                    private final Text nameText = new Text();

                    {
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        imageView.setFitHeight(25);
                        imageView.setFitWidth(25);
                        hBox.getChildren().addAll(checkBox, imageView, nameText); // CheckBox أول حاجة
                    }

                    @Override
                    protected void updateItem(Contact item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            imageView.setImage(item.getPhoto());
                            nameText.setText(item.getName());
                            checkBox.setSelected(selectedContacts.contains(item)); // Keep selected state
                            checkBox.setOnAction(e -> {
                                if (checkBox.isSelected()) {
                                    selectedContacts.add(item); // Add to selected contacts
                                } else {
                                    selectedContacts.remove(item); // Remove from selected contacts
                                }
                            });
                            setGraphic(hBox);
                        }
                    }
                });

                // Add the ListView to the dialog
                contactsDialog.getDialogPane().setContent(contactsListView);

                // Handle the Add button action
                contactsDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == addButton) {
                        System.out.println("Selected Contacts: " + selectedContacts.stream()
                                .map(Contact::getName)
                                .collect(Collectors.joining(", ")));
                    }
                    return null;
                });

                contactsDialog.showAndWait();
            });

            // Add components to the grid
            grid.add(groupImageBox, 0, 0, 2, 1);
            grid.add(new Label("Name:"), 0, 1);
            grid.add(nameField, 1, 1);
            grid.add(new Label("Description:"), 0, 2);
            grid.add(descriptionField, 1, 2);
            grid.add(chooseContactsButton, 0, 3, 2, 1);

            dialog.getDialogPane().setContent(grid);

            // Convert the result to a Map (Dummy Data)
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == createButton) {
                    Map<String, Object> groupData = new HashMap<>();
                    groupData.put("image", groupImageView.getImage());
                    groupData.put("name", nameField.getText());
                    groupData.put("description", descriptionField.getText());
                    groupData.put("members", selectedContacts); // Add selected contacts
                    return groupData;
                }
                return null;
            });

            Optional<Map<String, Object>> result = dialog.showAndWait();
            result.ifPresent(groupData -> {
                System.out.println("New Group: " + groupData.get("name") + " - " + groupData.get("description"));
                System.out.println("Members: " + ((ObservableList<?>) groupData.get("members")).stream()
                        .map(contact -> ((Contact) contact).getName())
                        .collect(Collectors.joining(", ")));
                // Save the group data or add it to a list
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in addGroupAction: " + e.getMessage());
        }
    }

    @FXML
    private void addCategoryAction() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Category");
            dialog.setHeaderText("Enter the new category name:");
            dialog.setContentText("Category:");
            dialog.setGraphic(null);

            setDialogLogo(dialog, 0.1, 0.3, 0.50, 0.0);
            // Apply custom styles to the dialog
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/liqaa/client/view/styles/style1.css").toExternalForm()
            );

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(categoryName -> {
                System.out.println("New Category: " + categoryName);
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in addCategoryAction: " + e.getMessage());
        }
    }

    @FXML
    private void removeCategoryAction() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Category");
            dialog.setHeaderText("Enter the category name to remove:");
            dialog.setContentText("Category:");
            dialog.setGraphic(null);

            setDialogLogo(dialog, 0.1, 0.3, 0.50, 0.0);
            // Apply custom styles to the dialog
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/liqaa/client/view/styles/style1.css").toExternalForm()
            );

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(categoryName -> {
                System.out.println("Category to remove: " + categoryName);
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in removeCategoryAction: " + e.getMessage());
        }
    }
}