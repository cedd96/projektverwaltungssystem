package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Modul;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

public class NewGroupContentVBox extends VBox {

    private static final double INPUTWIDTH = 400;

    private static Modul modul;

    private static String groupTitel;

    public NewGroupContentVBox() throws SQLException {
        setPadding(new Insets(100, 200, 100, 200));
        setAlignment(Pos.TOP_LEFT);
        setSpacing(10);
        setWidth(720);

        Label neueGruppe = new Label("Neue Gruppe erstellen");
        neueGruppe.setFont(Font.font("Verdana", FontWeight.BOLD, 26));

        HBox spacerBox1 = new HBox();
        spacerBox1.setPadding(new Insets(10));

        //TITEL
        Label titelLabel = new Label("Titel:");
        titelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        TextField titelInput = new TextField();
        titelInput.setFont(Font.font("Verdana", 12));
        titelInput.setPrefWidth(INPUTWIDTH);

        Region spacer1 = new Region();
        HBox titelBox = new HBox(5);
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        titelBox.getChildren().addAll(titelLabel, spacer1, titelInput);

        //MODUL
        Label modulLabel = new Label("Modul:");
        modulLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        ObservableList<Modul> cbOptions = FXCollections.observableArrayList(Modul.getAllModule(Main.getDB()));

        ComboBox<Modul> modulCb = new ComboBox<>();
        modulCb.setItems(cbOptions);
        modulCb.setPrefWidth(INPUTWIDTH);

        Region spacer2 = new Region();
        HBox modulBox = new HBox(5);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        modulBox.getChildren().addAll(modulLabel, spacer2, modulCb);

        getChildren().addAll(neueGruppe, spacerBox1, titelBox, modulBox);

        titelInput.setOnKeyReleased(keyEvent -> groupTitel = titelInput.getText());

        modulCb.setOnAction(actionEvent -> modul = modulCb.getSelectionModel().getSelectedItem());
    }

    public static Modul getModul() {
        return modul;
    }

    public static void resetModul() {
        modul = null;
    }

    public static String getGroupTitel() {
        return groupTitel;
    }

    public static void resetGroupTitel() {
        groupTitel = null;
    }

    public static void resetGroup() {
        resetGroupTitel();
        resetModul();
    }
}
