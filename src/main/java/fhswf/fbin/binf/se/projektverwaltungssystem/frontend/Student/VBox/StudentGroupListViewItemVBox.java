package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Antrag;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Projekt;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class StudentGroupListViewItemVBox extends VBox {
    private Gruppe gruppe;
    private Antrag antrag;

    private static final double FONTSIZE = 16;

    public StudentGroupListViewItemVBox(Gruppe gruppe) throws SQLException {
        this.gruppe = gruppe;
        this.antrag = Antrag.getAntragByGruppeOID(gruppe.getGruppeOID());

        //Widgettemplate
        Text titel = new Text("Titel:\t");
        titel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Text titelMsg = new Text(gruppe.getTitel());
        titelMsg.setFont(Font.font("Verdana", 16));

        HBox titelBox = new HBox();
        titelBox.getChildren().addAll(titel, titelMsg);

        Text modul = new Text("Modul:\t");
        modul.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Text modulMsg = new Text(gruppe.getModul().getModulname());
        modulMsg.setFont(Font.font("Verdana", 16));

        HBox modulBox = new HBox();
        modulBox.getChildren().addAll(modul, modulMsg);

        Text gruppeLabel = new Text("Gruppe:\t");
        gruppeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        String mitglieder = "";
        for(Student s : gruppe.getMitglieder()) {
            if(gruppe.getMitglieder().get(gruppe.getMitglieder().size() - 1).getMatrikelnummer().equals(s.getMatrikelnummer()))
                mitglieder += s.getVorname() + " " + s.getName();
            else
                mitglieder += s.getVorname() + " " + s.getName() + "\n";
        }

        Text gruppeValue = new Text(mitglieder);
        gruppeValue.setFont(Font.font("Verdana", FONTSIZE));

        HBox gruppeBox = new HBox();
        gruppeBox.getChildren().addAll(gruppeLabel, gruppeValue);

        Text statusLabel = new Text("Status:\t");
        statusLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Text statusValue = new Text();
        statusValue.setFont(Font.font("Verdana", FONTSIZE));

        if(antrag == null) {
            statusValue.setText("Es wurde noch kein Antrag angelegt");
            statusValue.setFill(Color.FIREBRICK);
        }
        else {
            statusValue.setText(antrag.getStatus().getBeschreibung());
            if(antrag.getStatus() == AntragStatus.ZUGELASSEN)
                statusValue.setFill(Color.GREEN);
            else if(antrag.getStatus() == AntragStatus.BEARBEITEN)
                statusValue.setFill(Color.ORANGE);
            else if(antrag.getStatus() == AntragStatus.VERSENDET)
                statusValue.setFill(Color.BLUE);
            else if(antrag.getStatus() == AntragStatus.FEHLT)
                statusValue.setFill(Color.FIREBRICK);
            else if(antrag.getStatus() == AntragStatus.ABGELEHNT)
                statusValue.setFill(Color.RED);
            else if(antrag.getStatus() == AntragStatus.ABGESCHLOSSEN)
                statusValue.setFill(Color.DARKGREEN);
        }

        HBox statusBox = new HBox();
        statusBox.getChildren().addAll(statusLabel, statusValue);

        //TERMINE
        Text terminLabel = new Text("Vortragstermin(e):\t");
        terminLabel.setFill(Color.GREEN);
        terminLabel.setFont(Font.font("Verdana", 16));

        Text terminDate = new Text();
        terminDate.setFill(Color.GREEN);
        terminDate.setFont(Font.font("Verdana", FONTSIZE));

        VBox terminVBox = new VBox();
        terminVBox.getChildren().addAll(terminDate);

        if(gruppe.getProjekt() != null) {
            if(gruppe.getProjekt().getTermin1() != null) {
                if(gruppe.getProjekt().getTermin1().compareTo(LocalDateTime.now()) > 0) {
                    terminDate.setText(Projekt.terminToString(gruppe.getProjekt().getTermin1()));
                }
            }
            else
                terminDate.setText("-");
            if(gruppe.getProjekt().getTermin2() != null) {
                if(gruppe.getProjekt().getTermin2().compareTo(LocalDateTime.now()) > 0) {
                    terminDate.setText(Projekt.terminToString(gruppe.getProjekt().getTermin1()) + "\n" + Projekt.terminToString(gruppe.getProjekt().getTermin2()));
                }
            }
        }
        else
            terminDate.setText("-");

        HBox terminHBox = new HBox();
        terminHBox.setPadding(new Insets(10, 0, 0, 0));
        terminHBox.getChildren().addAll(terminLabel, terminVBox);

        getChildren().addAll(titelBox, modulBox, gruppeBox, statusBox, terminHBox);
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

    public Antrag getAntrag() {
        return antrag;
    }

    public void setAntrag(Antrag antrag) {
        this.antrag = antrag;
    }
}
