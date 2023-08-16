package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.GridPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class RegisterGridPane extends GridPane {

    public RegisterGridPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25));

        Text titel = new Text("Registrierung");
        titel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        Label membershipLabel = new Label("Benutzen als: ");
        membershipLabel.setFont(Font.font("Verdana", 16));

        ObservableList<String> options = FXCollections.observableArrayList("Student/in", "Dozent/in");

        ComboBox<String> membershipCombo = new ComboBox<>();
        membershipCombo.setItems(options);

        Label vornameLabel = new Label("Vorname: ");
        vornameLabel.setFont(Font.font("Verdana", 16));

        TextField vornameInput = new TextField();
        vornameInput.setFont(Font.font("Verdana", 16));

        Label nameLabel = new Label("Name: ");
        nameLabel.setFont(Font.font("Verdana", 16));

        TextField nameInput = new TextField();
        nameInput.setFont(Font.font("Verdana", 16));

        Label usernameLabel = new Label("Benutzerkennung: ");
        usernameLabel.setFont(Font.font("Verdana", 16));

        TextField usernameInput = new TextField();
        usernameInput.setFont(Font.font("Verdana", 16));

        Label passwordLabel = new Label("Passwort: ");
        passwordLabel.setFont(Font.font("Verdana", 16));

        PasswordField passwordInput = new PasswordField();
        passwordInput.setFont(Font.font("Verdana", 16));

        TextField passwordVisInput = new TextField();
        passwordVisInput.setFont(Font.font("Verdana", 16));

        ToggleButton passwordVisButton = new ToggleButton();
        HBox passwordVisHBox = new HBox(10);
        passwordVisHBox.setAlignment(Pos.CENTER_RIGHT);
        passwordVisHBox.getChildren().add(passwordVisButton);

        Image visibilityOffIcon = new Image("https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_visibility_off_48px-512.png");
        ImageView visibilityOffIconView = new ImageView(visibilityOffIcon);
        visibilityOffIconView.setFitHeight(16);
        visibilityOffIconView.setFitWidth(16);

        Image visibiltyOnIcon = new Image("https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_visibility_48px-512.png");
        ImageView visibilityOnIconView = new ImageView(visibiltyOnIcon);
        visibilityOnIconView.setFitHeight(16);
        visibilityOnIconView.setFitWidth(16);

        passwordVisButton.setGraphic(visibilityOffIconView);

        Button backButton = new Button("Zurück");
        HBox backHBox = new HBox(10);
        backHBox.setAlignment(Pos.BOTTOM_LEFT);
        backHBox.getChildren().add(backButton);

        Button registerButton = new Button("Registrieren");
        HBox registerHBox = new HBox(10);
        registerHBox.setAlignment(Pos.BOTTOM_RIGHT);
        registerHBox.getChildren().add(registerButton);

        Region spacerRegion = new Region();
        HBox.setHgrow(spacerRegion, Priority.ALWAYS);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(backHBox, spacerRegion, registerHBox);

        final Label confirmation = new Label();
        confirmation.setFont(Font.font("Verdana", 12));

        add(titel, 0, 0, 2, 1);
        add(membershipLabel, 0, 1);
        add(membershipCombo, 1, 1);
        add(vornameLabel, 0, 2);
        add(vornameInput, 1, 2);
        add(nameLabel, 0, 3);
        add(nameInput, 1, 3);
        add(usernameLabel, 0, 4);
        add(usernameInput, 1, 4);
        add(passwordLabel, 0, 5);
        add(passwordInput, 1, 5);
        add(passwordVisHBox, 2, 5);
        add(buttonBox, 1, 6);
        add(confirmation, 0, 7, 3, 1);

        /**
         * Back to Login Screen
         */
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.getPrimaryStage().setScene(new Scene(new LoginGridPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
            }
        });

        /**
         * Register new user -> safe Data to DB -> Proceed to Landingpage
         */
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (LoginHandler.isUsernameUnique(usernameInput.getText()) && firstName(vornameInput.getText()) && lastName(nameInput.getText())) {
                        if (passwordVisButton.isSelected())
                            new LoginHandler(membershipCombo.getSelectionModel().getSelectedItem(), firstLetterToUpperCase(vornameInput.getText()), firstLetterToUpperCase(nameInput.getText()), usernameInput.getText(), passwordVisInput.getText());
                        else if (!passwordVisButton.isSelected())
                            new LoginHandler(membershipCombo.getSelectionModel().getSelectedItem(), firstLetterToUpperCase(vornameInput.getText()), firstLetterToUpperCase(nameInput.getText()), usernameInput.getText(), passwordInput.getText());

                        confirmation.setText("Sie wurden erfolgreich registriert!");
                        confirmation.setTextFill(Color.GREEN);
                    }
                    else if(!LoginHandler.isUsernameUnique(usernameInput.getText()) && firstName(vornameInput.getText()) && lastName(nameInput.getText())) {
                        confirmation.setText("Die Nutzerkennung " + usernameInput.getText() + " ist bereits vergeben.");
                        confirmation.setTextFill(Color.FIREBRICK);
                    }
                    else {
                        if(!firstName(vornameInput.getText())) {
                            vornameInput.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: red;");
                            confirmation.setText("Der Vorname hat das falsche Format.");
                            confirmation.setTextFill(Color.RED);
                        }
                        else {
                            vornameInput.setStyle("");
                        }

                        if(!lastName(nameInput.getText())) {
                            nameInput.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: red;");
                            confirmation.setText("Der Name hat das falsche Format");
                            confirmation.setTextFill(Color.RED);
                        }
                        else {
                            nameInput.setStyle("");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                            ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        /**
         * Toggle Password readabillity
         */
        passwordVisButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (passwordVisButton.isSelected()) {
                    passwordVisButton.setGraphic(visibilityOnIconView);
                    if (getChildren().contains(passwordInput))
                        getChildren().remove(passwordInput);
                    passwordVisInput.setText(passwordInput.getText());
                    add(passwordVisInput, 1, 5);
                } else if (!passwordVisButton.isSelected()) {
                    passwordVisButton.setGraphic(visibilityOffIconView);
                    if (getChildren().contains(passwordVisInput))
                        getChildren().remove(passwordVisInput);
                    passwordInput.setText(passwordVisInput.getText());
                    add(passwordInput, 1, 5);
                }
            }
        });
    }

    private static boolean firstName( String firstName ) {
        return firstName.matches("[a-zA-Z][a-zA-Z]*");
    }

    private static boolean lastName( String lastName ) {
        return lastName.matches("[a-zA-Z]+([ '-][a-zA-Z]+)*");
    }

    private String firstLetterToUpperCase(String stringToChange) {
        String firstLetter = stringToChange.substring(0, 1).toUpperCase();
        String remainingString = stringToChange.substring(1).toLowerCase();

        return firstLetter + remainingString;
    }
}
