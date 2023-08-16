package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Projekt;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.GroupOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupInfoContentVBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class GroupInfoOptionsHBox extends HBox {
    public GroupInfoOptionsHBox(GroupListViewItemVBox groupWidget) throws SQLException {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10));

        Button logoutButton = new Button("Abmelden");

        Button backButton = new Button("Zurück");

        Button sendButton = new Button("Evaluation Absenden");

        Accordion dateMenu = new Accordion();
        final DatePicker datePicker = new DatePicker();

        Button sendDateButton = new Button("Senden");
        HBox timeBtnBox = new HBox();
        timeBtnBox.setPadding(new Insets(5, 0, 0, 0));
        timeBtnBox.setAlignment(Pos.CENTER);
        timeBtnBox.getChildren().add(sendDateButton);


        ObservableList<LocalTime> timeList = FXCollections.observableArrayList(
                LocalTime.of(8, 0),
                LocalTime.of(9, 30),
                LocalTime.of(11, 0),
                LocalTime.of(12, 30),
                LocalTime.of(14, 0),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0),
                LocalTime.of(18, 30),
                LocalTime.of(20, 0)
        );

        Spinner<LocalTime> timer = new Spinner<>(timeList);
        HBox dateTimePickerBox = new HBox();
        dateTimePickerBox.getChildren().addAll(datePicker, timer);
        VBox dateBox = new VBox();
        dateBox.getChildren().addAll(dateTimePickerBox, timeBtnBox);
        dateBox.setAlignment(Pos.CENTER);

        TitledPane datePane = new TitledPane("Terminanfrage beantworten", dateBox);
        dateMenu.getPanes().add(datePane);

        final Label confirmation = new Label();
        confirmation.setWrapText(true);

        Region spacer = new Region();
        setHgrow(spacer, Priority.ALWAYS);

        logoutButton.setOnAction(actionEvent -> {
            LoginHandler.setCurrentUser(null);
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
            GroupInfoContentVBox.setStatusCBValue();
            GroupInfoContentVBox.setKommentarValue();
        });

        backButton.setOnAction(actionEvent -> {
            try {
                Main.getPrimaryStage().setScene(new Scene(new GroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Gruppenübersicht");
                GroupInfoContentVBox.setStatusCBValue();
                GroupInfoContentVBox.setKommentarValue();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        sendButton.setOnAction(actionEvent -> {
            try {
                if (GroupInfoContentVBox.getStatusCBValue() == null || GroupInfoContentVBox.getStatusCBValue().isEmpty()) {
                    confirmation.setText("Für eine Evaluation des Projektantrags müssen sie einen Status angeben.");
                    confirmation.setTextFill(Color.FIREBRICK);
                }
                else if(GroupInfoContentVBox.getStatusCBValue().equals(AntragStatus.BEARBEITEN.getBeschreibung()) && (GroupInfoContentVBox.getKommentarValue() == null || GroupInfoContentVBox.getKommentarValue().isEmpty())) {
                    confirmation.setText("Für die Bearbeitung des Projektantrags sind Verbesserungsvorschläge anzugeben.");
                    confirmation.setTextFill(Color.FIREBRICK);
                }
                else {
                    if(GroupInfoContentVBox.getStatusCBValue().equals(AntragStatus.ZUGELASSEN.getBeschreibung())) {
                        Projekt projekt = new Projekt(groupWidget.getGruppe().getGruppeOID());
                        groupWidget.getGruppe().setProjektoid(projekt.getProjektOID());
                    }
                    groupWidget.getAntrag().setStatus(GroupInfoContentVBox.getStatusCBValue());
                    groupWidget.getAntrag().setKommentar(GroupInfoContentVBox.getKommentarValue());
                    confirmation.setText("Die Evaluation wurde gesendet.");
                    confirmation.setTextFill(Color.GREEN);
                    getChildren().remove(sendButton);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        sendDateButton.setOnAction(actionEvent -> {
                try {
                    if(groupWidget.getGruppe().getProjekt().hasTerminAngefragt()) {
                        if (groupWidget.getGruppe().getProjekt().getTermin1() == null) {
                            groupWidget.getGruppe().getProjekt().setTermin1(LocalDateTime.of(datePicker.getValue(), timer.getValue()));
                        } else
                            groupWidget.getGruppe().getProjekt().setTermin2(LocalDateTime.of(datePicker.getValue(), timer.getValue()));
                        groupWidget.getGruppe().getProjekt().setTerminanfrageBeantwortet(true);
                        confirmation.setText("Der Termin wurde gesendet.");
                        confirmation.setTextFill(Color.GREEN);
                    }
                    else {
                        confirmation.setText("Keine offene Terminanfrage!");
                        confirmation.setTextFill(Color.FIREBRICK);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                            ButtonType.OK);
                    alert.showAndWait();
                }
        });

        getChildren().addAll(backButton);

        if(groupWidget.getGruppe().getProjekt() != null) {
            if(groupWidget.getGruppe().getProjekt().hasTerminAngefragt()) {
                getChildren().add(dateMenu);
            }
        }

        if(groupWidget.getAntrag().getStatus() == AntragStatus.VERSENDET)
            getChildren().add(sendButton);

        getChildren().addAll(confirmation, spacer, logoutButton);
    }
}
