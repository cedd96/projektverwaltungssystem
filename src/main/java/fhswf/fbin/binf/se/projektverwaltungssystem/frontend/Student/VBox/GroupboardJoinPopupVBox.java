package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppenbildung;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.GroupboardOptionsHBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class GroupboardJoinPopupVBox extends VBox {
    public GroupboardJoinPopupVBox(GroupboardListViewItemVBox groupWidget, GroupboardListViewVBox myListView) {
        setPadding(new Insets(25));
        setSpacing(10);

        PasswordField pinFeld = new PasswordField();
        pinFeld.setPromptText("PIN eingeben...");
        pinFeld.setFocusTraversable(false);

        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Zurück");

        Button joinButton = new Button("Beitreten");

        buttonBox.getChildren().addAll(backButton, joinButton);

        final Label confirmation = new Label();
        confirmation.setFont(Font.font("Verdana", 16));
        confirmation.setWrapText(true);

        getChildren().addAll(pinFeld, buttonBox, confirmation);

        backButton.setOnAction(actionEvent -> Main.getPopupStage().close());

        joinButton.setOnAction(actionEvent -> {
            if(pinFeld.getText().equals(String.valueOf(groupWidget.getGruppe().getPin()))) {
                try {
                    new Gruppenbildung((Student) LoginHandler.getCurrentUser(), groupWidget.getGruppe());
                    GroupboardOptionsHBox.setConfirmationText("Erfolgreich der Gruppe beigetreten.");
                    myListView.getListItems().remove(groupWidget);
                    Main.getPopupStage().close();
                } catch (SQLException er) {
                    er.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                            ButtonType.OK);
                    alert.showAndWait();
                }
            }
            else {
                confirmation.setText("Bitte geben sie eine korrrekte PIN ein");
                confirmation.setTextFill(Color.FIREBRICK);
            }
        });
    }
}
