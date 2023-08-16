package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Projekt;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Antrag;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GroupListViewItemVBox extends VBox {
    private Gruppe gruppe;
    private Antrag antrag;

    private static final double FONTSIZE = 16;

    public GroupListViewItemVBox(Gruppe gruppe) throws SQLException {
        this.gruppe = gruppe;
        this.antrag = Antrag.getAntragByGruppeOID(gruppe.getGruppeOID());

        //Widgettemplate
        Text titelLabel = new Text("Titel:\t");
        titelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Text titelValue = new Text(gruppe.getTitel());
        titelValue.setFont(Font.font("Verdana", FONTSIZE));

        HBox titelBox = new HBox();
        titelBox.getChildren().addAll(titelLabel, titelValue);

        Text modulLabel = new Text("Modul:\t");
        modulLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FONTSIZE));

        Text modulValue = new Text(gruppe.getModul().getModulname());
        modulValue.setFont(Font.font("Verdana", FONTSIZE));

        HBox modulBox = new HBox();
        modulBox.getChildren().addAll(modulLabel, modulValue);

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

        Text statusValue = new Text(antrag.getStatus().getBeschreibung());
        statusValue.setFont(Font.font("Verdana", FONTSIZE));

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

        HBox statusBox = new HBox();
        statusBox.getChildren().addAll(statusLabel, statusValue);

        Text terminLabel = new Text("Vortragstermin(e):\t");
        terminLabel.setFill(Color.GREEN);
        terminLabel.setFont(Font.font("Verdana", FONTSIZE));

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
            if(gruppe.getProjekt().getTermin2() != null) {
                if(gruppe.getProjekt().getTermin2().compareTo(LocalDateTime.now()) > 0) {
                    terminDate.setText(Projekt.terminToString(gruppe.getProjekt().getTermin1()) + "\n" + Projekt.terminToString(gruppe.getProjekt().getTermin2()));
                }
            }
        }

        HBox terminHBox = new HBox();
        terminHBox.setPadding(new Insets(10, 0, 0, 0));
        terminHBox.getChildren().addAll(terminLabel, terminVBox);

        Text terminValue = new Text();
        terminValue.setFill(Color.GREEN);
        terminValue.setFont(Font.font("Verdana", FONTSIZE));

        if(gruppe.getProjekt() != null) {
            if(gruppe.getProjekt().hasTerminAngefragt())
                terminValue.setText("Offene Terminanfrage");
        }

        HBox terminBox = new HBox();
        terminBox.setPadding(new Insets(10, 0, 0, 0));
        terminBox.getChildren().addAll(terminValue);

        getChildren().addAll(titelBox, modulBox, gruppeBox, statusBox);

        if(gruppe.getProjekt() != null) {
            if(gruppe.getProjekt().getTermin1() != null)
                getChildren().add(terminHBox);
        }

        if(gruppe.getProjekt() != null) {
            if(gruppe.getProjekt().hasTerminAngefragt())
                getChildren().add(terminBox);
        }
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public Antrag getAntrag() {
        return antrag;
    }
}
