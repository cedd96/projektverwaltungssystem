package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.MenuItem;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.OrganisationOverviewBorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.sql.SQLException;

public class OrganisationOverviewMenuItem extends MenuItem {
    private static final String MENUNAME = "_Organisationsübersicht";

    private static final KeyCombination MENU_ACCELERATOR = new KeyCodeCombination(
            KeyCode.W, KeyCombination.SHORTCUT_DOWN);

    public OrganisationOverviewMenuItem() {
        setText(MENUNAME);
        setAccelerator(MENU_ACCELERATOR);
        setOnAction((e) -> {
            try {
                Main.getPrimaryStage().setScene(new Scene(new OrganisationOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Organisationsübersicht");
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}