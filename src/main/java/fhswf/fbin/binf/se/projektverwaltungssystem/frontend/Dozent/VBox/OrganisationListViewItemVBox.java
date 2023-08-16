package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Organisation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class OrganisationListViewItemVBox extends VBox {

    private Organisation organisation;

    private int anzAnsprechpartner;

    private int anzProjekte;

    public OrganisationListViewItemVBox(Organisation organisation) throws SQLException {
        this.organisation = organisation;
        this.anzAnsprechpartner = organisation.getAllAnsprechpartner().size();
        this.anzProjekte = organisation.getAllAntraege(organisation.getAllAnsprechpartner()).size();

        //Widgettemplate
        //NAME
        Text nameLabel = new Text("Name:\t\t\t\t");
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Text nameValue = new Text(organisation.getName());
        nameValue.setFont(Font.font("Verdana", 16));

        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, nameValue);

        //ANZ ANSPRECHPARTNER
        Text ansprechpartnerAnzLabel = new Text("# Ansprechpartner:\t");
        ansprechpartnerAnzLabel.setFont(Font.font("Verdana", FontWeight.BOLD,16));

        Text ansprechpartnerAnzValue = new Text(String.valueOf(anzAnsprechpartner));
        ansprechpartnerAnzValue.setFont(Font.font("Verdana", 16));

        HBox ansprechpartnerAnzBox = new HBox();
        ansprechpartnerAnzBox.getChildren().addAll(ansprechpartnerAnzLabel, ansprechpartnerAnzValue);

        //ANZ PROJEKTE
        Text projektAnzLabel = new Text("# Projekte:\t\t\t");
        projektAnzLabel.setFont(Font.font("Verdana", FontWeight.BOLD,16));

        Text projektAnzValue = new Text(String.valueOf(anzProjekte));
        projektAnzValue.setFont(Font.font("Verdana", 16));

        HBox projektAnzBox = new HBox();
        projektAnzBox.getChildren().addAll(projektAnzLabel, projektAnzValue);

        getChildren().addAll(nameBox, ansprechpartnerAnzBox, projektAnzBox);
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public int getAnzAnsprechpartner() {
        return anzAnsprechpartner;
    }

    public int getAnzProjekte() {
        return anzProjekte;
    }
}
