package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.MenuItem;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.GridPane.LoginGridPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.StudentGroupOverviewBorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.sql.SQLException;

public class StudentGroupOverviewMenuItem extends MenuItem {
    private static final String MENUNAME = "_Gruppenübersicht";

    private static final KeyCombination MENU_ACCELERATOR = new KeyCodeCombination(
            KeyCode.Q, KeyCombination.SHORTCUT_DOWN);

    public StudentGroupOverviewMenuItem() {
        setText(MENUNAME);
        setAccelerator(MENU_ACCELERATOR);
        setOnAction((e) -> {
            try {
                Main.getPrimaryStage().setScene(new Scene(new StudentGroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Gruppenübersicht");
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}