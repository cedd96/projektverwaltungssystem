package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Ansprechpartner;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class StudentGroupInfoContentVBox extends VBox {

    private static final String BOXSTYLE = "-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1; -fx-border-radius: 1; -fx-background-color: white;";

    private static final double TEXTWIDTH = 430;

    private static final double FONTSIZE = 16;

    private static final double FONTSIZE_SMALLER = 14;

    private static final double FONTSIZE_HEADER = 26;

    private final Gruppe gruppe;

    private static Ansprechpartner antragAnsprechpartner;

    private static String antragTitel;

    private static String antragSkizze;

    private static String antragBeschreibungKurz;

    private static String antragBeschreibungDetails;

    private static Label statusValue;

    public StudentGroupInfoContentVBox(StudentGroupListViewItemVBox groupWidget) throws SQLException {
        this.gruppe = groupWidget.getGruppe();

        setPadding(new Insets(100));
        setAlignment(Pos.TOP_LEFT);
        setSpacing(10);
        setWidth(720);

        //ALLGEMEINE GRUPPENINFORMATIONEN
        Label infoHeader = new Label("Gruppeninformationen");
        infoHeader.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE_HEADER));
        getChildren().add(infoHeader);

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
        mitgliederValue.setFont(Font.font("Verdana", 12));
        mitgliederValue.setWrapText(true);

        String mitglieder = "";
        for(Student s : gruppe.getMitglieder()) {
            if(gruppe.getMitglieder().get(gruppe.getMitglieder().size() - 1).getMatrikelnummer().equals(s.getMatrikelnummer()))
                mitglieder += s.getVorname() + " " + s.getName();
            else
                mitglieder += s.getVorname() + " " + s.getName() + "\n";
        }

        mitgliederValue.setText(mitglieder);

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

        statusValue = new Label();
        statusValue.setFont(Font.font("Verdana", 12));
        statusValue.setWrapText(true);

        if(groupWidget.getAntrag() == null) {
            statusValue.setText("Es wurde noch kein Antrag angelegt");
            statusValue.setTextFill(Color.FIREBRICK);
        }
        else {
            statusValue.setText(groupWidget.getAntrag().getStatus().getBeschreibung());
            if(groupWidget.getAntrag().getStatus() == AntragStatus.ZUGELASSEN)
                statusValue.setTextFill(Color.GREEN);
            else if(groupWidget.getAntrag().getStatus() == AntragStatus.BEARBEITEN)
                statusValue.setTextFill(Color.ORANGE);
            else if(groupWidget.getAntrag().getStatus() == AntragStatus.VERSENDET)
                statusValue.setTextFill(Color.BLUE);
            else if(groupWidget.getAntrag().getStatus() == AntragStatus.FEHLT)
                statusValue.setTextFill(Color.FIREBRICK);
            else if(groupWidget.getAntrag().getStatus() == AntragStatus.ABGELEHNT)
                statusValue.setTextFill(Color.RED);
            else if(groupWidget.getAntrag().getStatus() == AntragStatus.ABGESCHLOSSEN)
                statusValue.setTextFill(Color.DARKGREEN);
        }

        BorderBox statusBorderBox = new BorderBox();
        statusBorderBox.getChildren().add(statusValue);

        Region spacer3 = new Region();
        HBox statusHBox = new HBox(5);
        statusHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        statusHBox.getChildren().addAll(statusLabel, spacer3, statusBorderBox);

        //PIN
        if(groupWidget.getGruppe().getMitglieder().get(0).getMatrikelnummer().equals(((Student) LoginHandler.getCurrentUser()).getMatrikelnummer())) {
            Label pinLabel = new Label("PIN:");
            pinLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

            Label pinValue = new Label(String.valueOf(gruppe.getPin()));
            pinValue.setFont(Font.font("Verdana",12));
            pinValue.setWrapText(true);

            BorderBox pinBorderBox = new BorderBox();
            pinBorderBox.getChildren().add(pinValue);

            Region pinSpacer = new Region();
            HBox pinHBox = new HBox(5);
            pinHBox.setAlignment(Pos.TOP_LEFT);
            HBox.setHgrow(pinSpacer, Priority.ALWAYS);
            pinHBox.getChildren().addAll(pinLabel, pinSpacer, pinBorderBox);

            getChildren().add(pinHBox);
        }

        getChildren().addAll(titelHBox, modulHBox, mitgliederHBox, statusHBox);


        //PROJEKTANTRAG
        Line separator = new Line(200, 30.5, Main.getWinWidth() - 200, 31);
        separator.getStrokeDashArray().addAll(2d);

        HBox separatorBox = new HBox();
        separatorBox.setPadding(new Insets(25, 0, 25, 0));
        separatorBox.getChildren().add(separator);

        //PROJEKTANTRAG
        Label antragHeader = new Label("Projektantrag");
        antragHeader.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE_HEADER));

        //ARBEITSTITEL
        Text arbeitstitelLabel = new Text("Arbeitstitel: ");
        arbeitstitelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        TextArea arbeitstitelInput = new TextArea();
        arbeitstitelInput.setFont(Font.font("Verdana", 12));
        arbeitstitelInput.setWrapText(true);
        arbeitstitelInput.setStyle(BOXSTYLE);
        arbeitstitelInput.setPrefWidth(TEXTWIDTH);
        arbeitstitelInput.setPrefRowCount(2);
        if(groupWidget.getAntrag() != null) {
            arbeitstitelInput.setText(groupWidget.getAntrag().getTitel());
            arbeitstitelInput.setEditable(groupWidget.getAntrag().getStatus() != AntragStatus.ABGESCHLOSSEN && groupWidget.getAntrag().getStatus() != AntragStatus.ZUGELASSEN && groupWidget.getAntrag().getStatus() != AntragStatus.VERSENDET);
        }

        Region spacer5 = new Region();
        HBox arbeitstitelHBox = new HBox(5);
        arbeitstitelHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer5, Priority.ALWAYS);
        arbeitstitelHBox.getChildren().addAll(arbeitstitelLabel, spacer5, arbeitstitelInput);

        //SKIZZE
        HBox skizzeHBox = new HBox(5);
        skizzeHBox.setAlignment(Pos.TOP_LEFT);
        Text skizzeText = new Text("Skizze: ");
        skizzeText.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        TextArea skizzeInput = new TextArea();
        skizzeInput.setFont(Font.font("Verdana", 12));
        skizzeInput.setWrapText(true);
        skizzeInput.setStyle(BOXSTYLE);
        skizzeInput.setPrefWidth(TEXTWIDTH);
        skizzeInput.setPrefRowCount(5);

        if(groupWidget.getAntrag() != null) {
            skizzeInput.setText(groupWidget.getAntrag().getSkizze());
            skizzeInput.setEditable(groupWidget.getAntrag().getStatus() != AntragStatus.ABGESCHLOSSEN && groupWidget.getAntrag().getStatus() != AntragStatus.ZUGELASSEN && groupWidget.getAntrag().getStatus() != AntragStatus.VERSENDET);
        }

        Region spacer6 = new Region();
        HBox.setHgrow(spacer6, Priority.ALWAYS);
        skizzeHBox.getChildren().addAll(skizzeText, spacer6, skizzeInput);

        //KURZE BESCHREIBUNG DES PROJEKTHINTERGRUNDS
        HBox beschreibungKurzHBox = new HBox(5);
        beschreibungKurzHBox.setAlignment(Pos.TOP_LEFT);
        Text beschreibungKurzText = new Text("Kurze Beschreibung\ndes Projekthintergrunds: ");
        beschreibungKurzText.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE_SMALLER));

        TextArea beschreibungKurzInput = new TextArea();
        beschreibungKurzInput.setFont(Font.font("Verdana", 12));
        beschreibungKurzInput.setWrapText(true);
        beschreibungKurzInput.setStyle(BOXSTYLE);
        beschreibungKurzInput.setPrefWidth(TEXTWIDTH);
        beschreibungKurzInput.setPrefRowCount(10);

        if(groupWidget.getAntrag() != null) {
            beschreibungKurzInput.setText(groupWidget.getAntrag().getBeschreibungKurz());
            beschreibungKurzInput.setEditable(groupWidget.getAntrag().getStatus() != AntragStatus.ABGESCHLOSSEN && groupWidget.getAntrag().getStatus() != AntragStatus.ZUGELASSEN && groupWidget.getAntrag().getStatus() != AntragStatus.VERSENDET);
        }

        Region spacer7 = new Region();
        HBox.setHgrow(spacer7, Priority.ALWAYS);
        beschreibungKurzHBox.getChildren().addAll(beschreibungKurzText, spacer7, beschreibungKurzInput);

        //DETAILIERTE BESCHREIBUNG DER PROJEKTINHALTE
        HBox beschreibungDetailHBox = new HBox(5);
        beschreibungDetailHBox.setAlignment(Pos.TOP_LEFT);
        Text beschreibungDetailText = new Text("Detailierte Beschreibung\nder Projektinhalte: ");
        beschreibungDetailText.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE_SMALLER));

        TextArea beschreibungDetailInput = new TextArea();
        beschreibungDetailInput.setFont(Font.font("Verdana", 12));
        beschreibungDetailInput.setWrapText(true);
        beschreibungDetailInput.setStyle(BOXSTYLE);
        beschreibungDetailInput.setPrefWidth(TEXTWIDTH);
        beschreibungDetailInput.setPrefRowCount(15);

        if(groupWidget.getAntrag() != null) {
            beschreibungDetailInput.setText(groupWidget.getAntrag().getBeschreibungDetails());
            beschreibungDetailInput.setEditable(groupWidget.getAntrag().getStatus() != AntragStatus.ABGESCHLOSSEN && groupWidget.getAntrag().getStatus() != AntragStatus.ZUGELASSEN && groupWidget.getAntrag().getStatus() != AntragStatus.VERSENDET);
        }

        Region spacer8 = new Region();
        HBox.setHgrow(spacer8, Priority.ALWAYS);
        beschreibungDetailHBox.getChildren().addAll(beschreibungDetailText, spacer8, beschreibungDetailInput);

        // KOMMENTAR
        Text kommentarLabel = new Text("Kommentar: ");
        kommentarLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Label kommentarValue = new Label(groupWidget.getAntrag().getKommentar() == null ? "" : groupWidget.getAntrag().getKommentar());
        kommentarValue.setFont(Font.font("Verdana", 12));
        kommentarValue.setWrapText(true);

        BorderBox kommentarBorderBox = new BorderBox();
        kommentarBorderBox.getChildren().add(kommentarValue);

        Region spacer111 = new Region();
        HBox kommentarHBox = new HBox(5);
        kommentarHBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(spacer111, Priority.ALWAYS);
        kommentarHBox.getChildren().addAll(kommentarLabel, spacer111, kommentarBorderBox);

        // Ansprechpartner
        HBox ansprechpartnerHBox = new HBox(5);
        ansprechpartnerHBox.setAlignment(Pos.TOP_LEFT);

        Text ansprechpartner = new Text("Ansprechpartner:");
        ansprechpartner.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));


        Region spacer9 = new Region();
        HBox.setHgrow(spacer9, Priority.ALWAYS);
        ansprechpartnerHBox.getChildren().addAll(ansprechpartner, spacer9);

        ObservableList<Ansprechpartner> cbOptions = FXCollections.observableArrayList(Ansprechpartner.getAllAnsprechpartner(Main.getDB()));
        ComboBox<Ansprechpartner> cbAnsprechpartner = new ComboBox<>();
        cbAnsprechpartner.setPrefWidth(TEXTWIDTH);
        cbAnsprechpartner.setItems(cbOptions);

        TextField ansprechpartnerMsg = new TextField();
        ansprechpartnerMsg.setFont(Font.font("Verdana", 12));
        ansprechpartnerMsg.setPrefWidth(TEXTWIDTH);
        ansprechpartnerMsg.setStyle(BOXSTYLE);
        ansprechpartnerMsg.setEditable(false);

        if(groupWidget.getAntrag() == null) {
            ansprechpartnerHBox.getChildren().add(cbAnsprechpartner);
        }
        else {
            if(groupWidget.getAntrag().getStatus() != AntragStatus.ABGESCHLOSSEN && groupWidget.getAntrag().getStatus() != AntragStatus.ZUGELASSEN && groupWidget.getAntrag().getStatus() != AntragStatus.VERSENDET) {
                ansprechpartnerHBox.getChildren().add(cbAnsprechpartner);

                if(groupWidget.getAntrag().getAnsprechpartner() != null)
                    cbAnsprechpartner.setValue(groupWidget.getAntrag().getAnsprechpartner());
            }
            else {
                ansprechpartnerMsg.setText(groupWidget.getAntrag().getAnsprechpartner().getVorname() + " " + groupWidget.getAntrag().getAnsprechpartner().getName());
                ansprechpartnerHBox.getChildren().add(ansprechpartnerMsg);
            }
        }

        getChildren().addAll(separatorBox, antragHeader, ansprechpartnerHBox, arbeitstitelHBox, skizzeHBox, beschreibungKurzHBox, beschreibungDetailHBox);

        if(kommentarValue.getText() != null && !kommentarValue.getText().isEmpty())
            getChildren().add(kommentarHBox);

        if(groupWidget.getAntrag() != null) {
            if(groupWidget.getAntrag().getAnsprechpartner() != null)
                antragAnsprechpartner = groupWidget.getAntrag().getAnsprechpartner();
            if(groupWidget.getAntrag().getTitel() != null)
                antragTitel = groupWidget.getAntrag().getTitel();
            if(groupWidget.getAntrag().getSkizze() != null)
                antragSkizze = groupWidget.getAntrag().getSkizze();
            if(groupWidget.getAntrag().getBeschreibungKurz() != null)
                antragBeschreibungKurz = groupWidget.getAntrag().getBeschreibungKurz();
            if(groupWidget.getAntrag().getBeschreibungDetails() != null)
                antragBeschreibungDetails = groupWidget.getAntrag().getBeschreibungDetails();
        }

        Main.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> separator.setEndX((Double) newVal - 200));

        cbAnsprechpartner.setOnAction(actionEvent -> antragAnsprechpartner = cbAnsprechpartner.getSelectionModel().getSelectedItem());

        arbeitstitelInput.setOnKeyReleased(keyEvent -> antragTitel = arbeitstitelInput.getText());

        skizzeInput.setOnKeyReleased(keyEvent -> antragSkizze = skizzeInput.getText());

        beschreibungKurzInput.setOnKeyReleased(keyEvent -> antragBeschreibungKurz = beschreibungKurzInput.getText());

        beschreibungDetailInput.setOnKeyReleased(keyEvent -> antragBeschreibungDetails = beschreibungDetailInput.getText());
    }

    public Gruppe getGruppe() {
        return gruppe;
    }



    public static Ansprechpartner getAntragAnsprechpartner() {
        return antragAnsprechpartner;
    }

    public static void resetAntragAnsprechpartner() {
        antragAnsprechpartner = null;
    }

    public static String getAntragTitel() {
        return antragTitel;
    }

    public static void resetAntragTitel() {
        antragTitel = null;
    }

    public static String getAntragSkizze() {
        return antragSkizze;
    }

    public static void resetAntragSkizze() {
        antragSkizze = null;
    }

    public static String getAntragBeschreibungKurz() {
        return antragBeschreibungKurz;
    }

    public static void resetAntragBeschreibungKurz() {
        antragBeschreibungKurz = null;
    }

    public static String getAntragBeschreibungDetails() {
        return antragBeschreibungDetails;
    }

    public static void resetAntragBeschreibungDetails() {
        antragBeschreibungDetails = null;
    }

    public static void resetAntrag() {
        resetAntragAnsprechpartner();
        resetAntragBeschreibungDetails();
        resetAntragBeschreibungKurz();
        resetAntragSkizze();
        resetAntragTitel();
    }

    private static class BorderBox extends VBox {
        public BorderBox() {
            setPrefWidth(TEXTWIDTH);
            setStyle(BOXSTYLE);
            setPadding(new Insets(5));
        }
    }

    public static void updateStatus(AntragStatus status) {
        statusValue.setText(status.getBeschreibung());

        if(status == AntragStatus.ZUGELASSEN)
            statusValue.setTextFill(Color.GREEN);
        else if(status == AntragStatus.BEARBEITEN)
            statusValue.setTextFill(Color.ORANGE);
        else if(status == AntragStatus.VERSENDET)
            statusValue.setTextFill(Color.BLUE);
        else if(status == AntragStatus.FEHLT)
            statusValue.setTextFill(Color.FIREBRICK);
        else if(status == AntragStatus.ABGELEHNT)
            statusValue.setTextFill(Color.RED);
        else if(status == AntragStatus.ABGESCHLOSSEN)
            statusValue.setTextFill(Color.DARKGREEN);
    }
}
