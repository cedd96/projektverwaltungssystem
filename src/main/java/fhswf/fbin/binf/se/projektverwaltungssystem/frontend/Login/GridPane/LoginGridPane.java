package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.GridPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.GroupOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.StudentGroupOverviewBorderPane;
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

public class LoginGridPane extends GridPane {
    public LoginGridPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25));

        Text titel = new Text("Anmeldung");
        titel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

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

        Button registerButton = new Button("Registrieren");
        HBox registerHBox = new HBox(10);
        registerHBox.setAlignment(Pos.BOTTOM_LEFT);
        registerHBox.getChildren().add(registerButton);

        Button loginButton = new Button("Anmelden");
        HBox loginHBox = new HBox(10);
        loginHBox.setAlignment(Pos.BOTTOM_RIGHT);
        loginHBox.getChildren().add(loginButton);

        Region spacerRegion = new Region();
        HBox.setHgrow(spacerRegion, Priority.ALWAYS);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(registerHBox, spacerRegion, loginHBox);

        final Label confirmation = new Label();
        confirmation.setFont(Font.font("Verdana", 12));

        add(titel, 0, 0, 2, 1);
        add(usernameLabel, 0, 1);
        add(usernameInput, 1, 1);
        add(passwordLabel, 0, 2);
        add(passwordInput, 1, 2);
        add(passwordVisHBox, 2, 2);
        add(buttonBox, 1, 3);
        add(confirmation, 0, 4, 3, 1);


        registerButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new RegisterGridPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Registrierung");
        });

        loginButton.setOnAction(actionEvent -> {
            try {
                if (passwordVisButton.isSelected()) {
                    if (LoginHandler.usernameExists(usernameInput.getText()) && LoginHandler.isPasswordValid(usernameInput.getText(), passwordVisInput.getText())) { // Anmeldedaten korrekt
                        LoginHandler.setCurrentUser(LoginHandler.loadUserFromDB(usernameInput.getText()));
                        if (LoginHandler.getCurrentUser() instanceof Student) {
                            Main.getPrimaryStage().setScene(new Scene(new StudentGroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                        } else if (LoginHandler.getCurrentUser() instanceof Dozent) {
                            Main.getPrimaryStage().setScene(new Scene(new GroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                        }
                        Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + usernameInput.getText() + "/Gruppenübersicht");
                        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                    } else if (LoginHandler.usernameExists(usernameInput.getText()) && !LoginHandler.isPasswordValid(usernameInput.getText(), passwordVisInput.getText())) { // Passwort falsch
                        confirmation.setText("Das eingegebene Passwort ist falsch.");
                        confirmation.setTextFill(Color.FIREBRICK);
                    } else if (!LoginHandler.usernameExists(usernameInput.getText())) { // Username falsch
                        confirmation.setText("Es ist kein Nutzer mit der Nutzerkennung " + usernameInput.getText() + " registriert.");
                        confirmation.setTextFill(Color.FIREBRICK);
                    }
                } else if (!passwordVisButton.isSelected()) {
                    if (LoginHandler.usernameExists(usernameInput.getText()) && LoginHandler.isPasswordValid(usernameInput.getText(), passwordInput.getText())) {
                        LoginHandler.setCurrentUser(LoginHandler.loadUserFromDB(usernameInput.getText()));
                        if (LoginHandler.getCurrentUser() instanceof Student) {
                            Main.getPrimaryStage().setScene(new Scene(new StudentGroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                        } else if (LoginHandler.getCurrentUser() instanceof Dozent) {
                            Main.getPrimaryStage().setScene(new Scene(new GroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                        }
                        Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + usernameInput.getText() + "/Gruppenübersicht");
                        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                    } else if (LoginHandler.usernameExists(usernameInput.getText()) && !LoginHandler.isPasswordValid(usernameInput.getText(), passwordInput.getText())) {
                        confirmation.setText("Das eingegebene Passwort ist falsch.");
                        confirmation.setTextFill(Color.FIREBRICK);
                    } else if (!LoginHandler.usernameExists(usernameInput.getText())) {
                        confirmation.setText("Es ist kein Nutzer mit der Nutzerkennung " + usernameInput.getText() + " registriert.");
                        confirmation.setTextFill(Color.FIREBRICK);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        passwordVisButton.setOnAction(actionEvent ->

        {
            if (passwordVisButton.isSelected()) {
                passwordVisButton.setGraphic(visibilityOnIconView);
                getChildren().remove(passwordInput);
                passwordVisInput.setText(passwordInput.getText());
                add(passwordVisInput, 1, 2);
            } else if (!passwordVisButton.isSelected()) {
                passwordVisButton.setGraphic(visibilityOffIconView);
                getChildren().remove(passwordVisInput);
                passwordInput.setText(passwordVisInput.getText());
                add(passwordInput, 1, 2);
            }
        });
    }
}
