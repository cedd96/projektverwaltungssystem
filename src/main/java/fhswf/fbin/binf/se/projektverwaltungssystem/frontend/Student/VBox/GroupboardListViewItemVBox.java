package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Antrag;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class GroupboardListViewItemVBox extends VBox {
    private final Gruppe gruppe;

    private final Antrag antrag;

    private static final double FONTSIZE = 16;

    public GroupboardListViewItemVBox(Gruppe gruppe) throws SQLException {
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

        getChildren().addAll(titelBox, modulBox, gruppeBox);
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public Antrag getAntrag() {
        return antrag;
    }
}
