package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class GroupInfoContentVBox extends VBox {

    private static final String BOXSTYLE = "-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1; -fx-border-radius: 1; -fx-background-color: white;";

    private static final double TEXTWIDTH = 430;

    private static final double FONTSIZE = 16;

    private static final double FONTSIZE_SMALLER = 14;

    private static String statusCBValue = null;

    private static String kommentarValue = null;

    public GroupInfoContentVBox(GroupListViewItemVBox groupWidget) throws SQLException {
        setPadding(new Insets(100));
        setAlignment(Pos.TOP_LEFT);
        setSpacing(10);
        setWidth(720);

        //GRUPPENTITEL
        Text titelLabel = new Text("Titel: ");
        titelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label titelValue = new Label(groupWidget.getGruppe().getTitel());
        titelValue.setFont(Font.font("Verdana", 12));
        titelValue.setWrapText(true);

        BorderBox titelBorderBox = new BorderBox();
        titelBorderBox.getChildren().add(titelValue);

        Region spacer = new Region();
        HBox titelHBox = new HBox(5);
        titelHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titelHBox.getChildren().addAll(titelLabel, spacer, titelBorderBox);

        //MODULNAME
        Text modulLabel = new Text("Modulname: ");
        modulLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label modulValue = new Label(groupWidget.getGruppe().getModul().getModulname());
        modulValue.setFont(Font.font("Verdana", 12));
        modulValue.setWrapText(true);

        BorderBox modulBorderBox = new BorderBox();
        modulBorderBox.getChildren().add(modulValue);

        Region spacer1 = new Region();
        HBox modulHBox = new HBox(5);
        modulHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        modulHBox.getChildren().addAll(modulLabel, spacer1, modulBorderBox);

        //MITGLIEDER
        Text mitgliederLabel = new Text("Mitglieder: ");
        mitgliederLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label mitgliederValue = new Label();
        for (Student s : groupWidget.getGruppe().getMitglieder())
            mitgliederValue.setText(mitgliederValue.getText() + s.getVorname() + " " + s.getName() + "\n");
        mitgliederValue.setFont(Font.font("Verdana", 12));
        mitgliederValue.setWrapText(true);

        BorderBox mitgliederBorderBox = new BorderBox();
        mitgliederBorderBox.getChildren().add(mitgliederValue);

        Region spacer2 = new Region();
        HBox mitgliederHBox = new HBox(5);
        mitgliederHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        mitgliederHBox.getChildren().addAll(mitgliederLabel, spacer2, mitgliederBorderBox);

        //STATUS
        Text statusLabel = new Text("Status: ");
        statusLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label statusValue = new Label(groupWidget.getAntrag().getStatus().getBeschreibung());
        statusValue.setFont(Font.font("Verdana", 12));
        statusValue.setWrapText(true);

        if (groupWidget.getAntrag().getStatus() == AntragStatus.ZUGELASSEN)
            statusValue.setTextFill(Color.GREEN);
        else if (groupWidget.getAntrag().getStatus() == AntragStatus.BEARBEITEN)
            statusValue.setTextFill(Color.ORANGE);
        else if (groupWidget.getAntrag().getStatus() == AntragStatus.VERSENDET)
            statusValue.setTextFill(Color.BLUE);
        else if (groupWidget.getAntrag().getStatus() == AntragStatus.FEHLT)
            statusValue.setTextFill(Color.FIREBRICK);
        else if (groupWidget.getAntrag().getStatus() == AntragStatus.ABGELEHNT)
            statusValue.setTextFill(Color.RED);

        BorderBox statusBorderBox = new BorderBox();
        statusBorderBox.getChildren().add(statusValue);

        Region spacer3 = new Region();
        HBox statusHBox = new HBox(5);
        statusHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        statusHBox.getChildren().addAll(statusLabel, spacer3, statusBorderBox);

        //PROJEKTANTRAG
        Label antragLabel = new Label("Projektantrag");
        antragLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 26));

        //ARBEITSTITEL
        Text arbeitstitelLabel = new Text("Arbeitstitel: ");
        arbeitstitelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label arbeitstitelValue = new Label(groupWidget.getAntrag().getTitel());
        arbeitstitelValue.setFont(Font.font("Verdana", 12));
        arbeitstitelValue.setWrapText(true);

        BorderBox arbeitstitelBorderBox = new BorderBox();
        arbeitstitelBorderBox.getChildren().add(arbeitstitelValue);

        Region spacer4 = new Region();
        HBox arbeitstitelHBox = new HBox(5);
        arbeitstitelHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        arbeitstitelHBox.getChildren().addAll(arbeitstitelLabel, spacer4, arbeitstitelBorderBox);

        //SKIZZE
        Text skizzeLabel = new Text("Skizze: ");
        skizzeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label skizzeValue = new Label(groupWidget.getAntrag().getSkizze());
        skizzeValue.setFont(Font.font("Verdana", 12));
        skizzeValue.setWrapText(true);

        BorderBox skizzeBorderBox = new BorderBox();
        skizzeBorderBox.getChildren().add(skizzeValue);

        Region spacer5 = new Region();
        HBox skizzeHBox = new HBox(5);
        skizzeHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer5, Priority.ALWAYS);
        skizzeHBox.getChildren().addAll(skizzeLabel, spacer5, skizzeBorderBox);

        //KURZE BESCHREIBUNG DES PROJEKTHINTERGRUNDS
        Text beschreibungKurzLabel = new Text("Kurze Beschreibung\ndes Projekthintergrunds: ");
        beschreibungKurzLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE_SMALLER));

        Label beschreibungKurzValue = new Label(groupWidget.getAntrag().getBeschreibungKurz());
        beschreibungKurzValue.setFont(Font.font("Verdana", 12));
        beschreibungKurzValue.setWrapText(true);

        BorderBox beschreibungKurzBorderBox = new BorderBox();
        beschreibungKurzBorderBox.getChildren().add(beschreibungKurzValue);

        Region spacer6 = new Region();
        HBox beschreibungKurzHBox = new HBox(5);
        beschreibungKurzHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer6, Priority.ALWAYS);
        beschreibungKurzHBox.getChildren().addAll(beschreibungKurzLabel, spacer6, beschreibungKurzBorderBox);

        //DETAILIERTE BESCHREIBUNG DER PROJEKTINHALTE
        Text beschreibungDetailLabel = new Text("Detailierte Beschreibung\nder Projektinhalte: ");
        beschreibungDetailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE_SMALLER));

        Label beschreibungDetailValue = new Label(groupWidget.getAntrag().getBeschreibungDetails());
        beschreibungDetailValue.setFont(Font.font("Verdana", 12));
        beschreibungDetailValue.setWrapText(true);

        BorderBox beschreibungDetailBorderBox = new BorderBox();
        beschreibungDetailBorderBox.getChildren().add(beschreibungDetailValue);

        Region spacer7 = new Region();
        HBox beschreibungDetailHBox = new HBox(5);
        beschreibungDetailHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer7, Priority.ALWAYS);
        beschreibungDetailHBox.getChildren().addAll(beschreibungDetailLabel, spacer7, beschreibungDetailBorderBox);

        //ANSPRECHPARTNER
        Text ansprechpartnerLabel = new Text("Ansprechpartner: ");
        ansprechpartnerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label ansprechpartnerValue = new Label(groupWidget.getAntrag().getAnsprechpartner().getVorname() + " " + groupWidget.getAntrag().getAnsprechpartner().getName());
        ansprechpartnerValue.setFont(Font.font("Verdana", 12));
        ansprechpartnerValue.setWrapText(true);

        BorderBox ansprechpartnerBorderBox = new BorderBox();
        ansprechpartnerBorderBox.getChildren().add(ansprechpartnerValue);

        Region spacer9 = new Region();
        HBox ansprechpartnerHBox = new HBox(5);
        ansprechpartnerHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer9, Priority.ALWAYS);
        ansprechpartnerHBox.getChildren().addAll(ansprechpartnerLabel, spacer9, ansprechpartnerBorderBox);

        //ANTWORT FORMULAR
        TextArea kommentarTextArea = new TextArea();
        kommentarTextArea.setPromptText("Anregungen für Antragsergänzungen...");
        kommentarTextArea.setPrefWidth(TEXTWIDTH);
        kommentarTextArea.setWrapText(true);

        if(groupWidget.getAntrag() != null) {
            if(groupWidget.getAntrag().getKommentar() != null && !groupWidget.getAntrag().getKommentar().isEmpty())
                kommentarTextArea.setText(groupWidget.getAntrag().getKommentar());
        }

        ObservableList<String> status = FXCollections.observableArrayList(
                "",
                "Antrag angenommen",
                "Antrag abgelehnt",
                "Der Antrag muss überarbeitet werden"
        );

        ComboBox<String> statusChoices = new ComboBox<>();
        statusChoices.setPromptText("Status ändern");
        statusChoices.setItems(status);

        Region spacer8 = new Region();
        HBox antwortHBox = new HBox(5);
        antwortHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer8, Priority.ALWAYS);

        Label kommentarLabel = new Label("Kommentar:");
        kommentarLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        if (groupWidget.getAntrag().getStatus() == AntragStatus.VERSENDET) {
            kommentarTextArea.setEditable(true);
            antwortHBox.getChildren().addAll(statusChoices, spacer8, kommentarTextArea);
        } else {
            kommentarTextArea.setEditable(false);
            antwortHBox.getChildren().addAll(kommentarLabel, spacer8, kommentarTextArea);
        }

        HBox spacerBox = new HBox();
        spacerBox.setPadding(new Insets(25, 0, 25, 0));

        getChildren().addAll(titelHBox, modulHBox, mitgliederHBox, statusHBox);

        Line separator = new Line(200, 30.5, Main.getWinWidth() - 200, 31);
        separator.getStrokeDashArray().addAll(2d);

        HBox separatorBox = new HBox();
        separatorBox.setPadding(new Insets(25, 0, 25, 0));
        separatorBox.getChildren().add(separator);

        if (groupWidget.getAntrag().getStatus() != AntragStatus.FEHLT) {
            getChildren().addAll(separatorBox, antragLabel, ansprechpartnerHBox, arbeitstitelHBox, skizzeHBox, beschreibungKurzHBox, beschreibungDetailHBox);
            if (groupWidget.getAntrag().getStatus() == AntragStatus.VERSENDET) {
                getChildren().addAll(spacerBox);
            }
            getChildren().add(antwortHBox);
        }

        Main.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> separator.setEndX((Double) newVal - 200));

        statusChoices.setOnAction(actionEvent -> {
            statusCBValue = statusChoices.getSelectionModel().getSelectedItem();
        });

        kommentarTextArea.setOnKeyReleased(keyEvent -> kommentarValue = kommentarTextArea.getText());
    }

    public static String getStatusCBValue() {
        return statusCBValue;
    }

    public static String getKommentarValue() {
        return kommentarValue;
    }

    public static void setStatusCBValue() {
        statusCBValue = null;
    }

    public static void setKommentarValue() {
        kommentarValue = null;
    }

    private static class BorderBox extends VBox {
        public BorderBox() {
            setPrefWidth(TEXTWIDTH);
            setStyle(BOXSTYLE);
            setPadding(new Insets(5));
        }
    }
}
