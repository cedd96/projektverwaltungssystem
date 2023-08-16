package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppenbildung;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.StudentGroupOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.NewGroupContentVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewVBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class NewGroupOptionsHBox extends HBox {
    public NewGroupOptionsHBox() {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10));

        Button logoutButton = new Button("Abmelden");

        Button backButton = new Button("Zurück");

        Button createButton = new Button("Gruppe erstellen");

        final Label confirmation = new Label();
        confirmation.setWrapText(true);

        Region spacer = new Region();
        setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(backButton, createButton, confirmation, spacer, logoutButton);

        logoutButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
            NewGroupContentVBox.resetGroup();
        });

        backButton.setOnAction(actionEvent -> {
            try {
                Main.getPrimaryStage().setScene(new Scene(new StudentGroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Gruppenübersicht");
                NewGroupContentVBox.resetGroup();
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        createButton.setOnAction(actionEvent -> {
            try {
                boolean titelCheck = false;
                if (NewGroupContentVBox.getGroupTitel() == null || NewGroupContentVBox.getGroupTitel().isEmpty() ||
                        NewGroupContentVBox.getModul() == null) {
                    confirmation.setText("Gruppentitel und Modul sind Pflichtfelder.");
                    confirmation.setTextFill(Color.FIREBRICK);
                } else {
                    for (Gruppe g : Gruppe.getAllGroups(Main.getDB())) {
                        if(g.getTitel().equals(NewGroupContentVBox.getGroupTitel())) {
                            confirmation.setText("Der Gruppentitel ist bereits vergeben.");
                            confirmation.setTextFill(Color.FIREBRICK);
                            titelCheck = true;
                            break;
                        }
                    }
                    if(!titelCheck) {
                        new Gruppenbildung((Student) LoginHandler.getCurrentUser(), new Gruppe(NewGroupContentVBox.getGroupTitel(), NewGroupContentVBox.getModul().getModulOID()));
                        confirmation.setText("Die Gruppe wurde erstellt!");
                        confirmation.setTextFill(Color.GREEN);
                        NewGroupContentVBox.resetGroup();
                    }
                }
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
