package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Antrag;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.StudentGroupOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupInfoContentVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class StudentGroupInfoOptionsHBox extends HBox {
    public StudentGroupInfoOptionsHBox(StudentGroupListViewItemVBox groupWidget) throws SQLException {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10));

        Button logoutButton = new Button("Abmelden");

        Button backButton = new Button("Zurück");

        Button sendButton = new Button("Antrag Absenden");

        Button terminButton = new Button("Terminanfrage senden");

        Button finishButton = new Button("Projekt abschließen");

        final Label confirmation = new Label();
        confirmation.setWrapText(true);

        Region spacer = new Region();
        setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(backButton);

        if (groupWidget.getAntrag() == null) {
            getChildren().addAll(sendButton);
        } else {
            if (groupWidget.getAntrag().getStatus() == AntragStatus.FEHLT || groupWidget.getAntrag().getStatus() == AntragStatus.BEARBEITEN)
                getChildren().addAll(sendButton);
            else if(groupWidget.getAntrag().getStatus() == AntragStatus.ZUGELASSEN)
                if(groupWidget.getGruppe().getProjekt() != null) {
                    if(groupWidget.getGruppe().getProjekt().getTermin1() == null || groupWidget.getGruppe().getProjekt().getTermin2() == null)
                        getChildren().addAll(terminButton);
                    getChildren().add(finishButton);
                }
        }

        getChildren().addAll(confirmation, spacer, logoutButton);

        logoutButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
            StudentGroupInfoContentVBox.resetAntrag();
        });

        terminButton.setOnAction(actionEvent -> {
            try {
                groupWidget.getGruppe().getProjekt().setTerminAngefragt(true);
                confirmation.setText("Terminanfrage wurde gesendet.");
                confirmation.setTextFill(Color.GREEN);
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        backButton.setOnAction(actionEvent -> {
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
            StudentGroupInfoContentVBox.resetAntrag();
        });

        sendButton.setOnAction(actionEvent -> {
            try {
                if (StudentGroupInfoContentVBox.getAntragAnsprechpartner() == null ||
                        StudentGroupInfoContentVBox.getAntragTitel() == null || StudentGroupInfoContentVBox.getAntragTitel().isEmpty() ||
                        StudentGroupInfoContentVBox.getAntragSkizze() == null || StudentGroupInfoContentVBox.getAntragSkizze().isEmpty() ||
                        StudentGroupInfoContentVBox.getAntragBeschreibungKurz() == null || StudentGroupInfoContentVBox.getAntragBeschreibungKurz().isEmpty() ||
                        StudentGroupInfoContentVBox.getAntragBeschreibungDetails() == null || StudentGroupInfoContentVBox.getAntragBeschreibungDetails().isEmpty()) {
                    confirmation.setText("Der Antrag wurde nicht vollständig ausgefüllt.");
                    confirmation.setTextFill(Color.FIREBRICK);
                } else {
                    if (groupWidget.getAntrag() != null) {
                        groupWidget.getAntrag().setAnsprechpartner(StudentGroupInfoContentVBox.getAntragAnsprechpartner());
                        groupWidget.getAntrag().setTitel(StudentGroupInfoContentVBox.getAntragTitel());
                        groupWidget.getAntrag().setSkizze(StudentGroupInfoContentVBox.getAntragSkizze());
                        groupWidget.getAntrag().setBeschreibungKurz(StudentGroupInfoContentVBox.getAntragBeschreibungKurz());
                        groupWidget.getAntrag().setBeschreibungDetails(StudentGroupInfoContentVBox.getAntragBeschreibungDetails());
                        groupWidget.getAntrag().setStatus(AntragStatus.VERSENDET);
                    } else {
                        groupWidget.setAntrag(new Antrag(
                                groupWidget.getGruppe(),
                                StudentGroupInfoContentVBox.getAntragAnsprechpartner(),
                                StudentGroupInfoContentVBox.getAntragTitel(),
                                StudentGroupInfoContentVBox.getAntragSkizze(),
                                StudentGroupInfoContentVBox.getAntragBeschreibungKurz(),
                                StudentGroupInfoContentVBox.getAntragBeschreibungDetails(),
                                AntragStatus.VERSENDET));
                    }
                    confirmation.setText("Der Antrag wurde versendet.");
                    confirmation.setTextFill(Color.GREEN);
                    StudentGroupInfoContentVBox.resetAntrag();
                    StudentGroupInfoContentVBox.updateStatus(AntragStatus.VERSENDET);
                    getChildren().remove(sendButton);
                }
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        finishButton.setOnAction(actionEvent -> {
            try {
                groupWidget.getAntrag().setStatus(AntragStatus.ABGESCHLOSSEN);
                getChildren().remove(finishButton);
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
