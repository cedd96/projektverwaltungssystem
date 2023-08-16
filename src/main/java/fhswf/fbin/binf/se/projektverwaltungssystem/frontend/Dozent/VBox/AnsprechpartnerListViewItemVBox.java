package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Ansprechpartner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class AnsprechpartnerListViewItemVBox extends VBox {

    private Ansprechpartner ansprechpartner;

    private int anzProjekte;

    public AnsprechpartnerListViewItemVBox(Ansprechpartner ansprechpartner) throws SQLException {
        this.ansprechpartner = ansprechpartner;
        this.anzProjekte = ansprechpartner.getProjectCount();
        //Widgettemplate
        Text nameLabel = new Text("Name:\t\t");
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Text nameValue = new Text(ansprechpartner.getVorname() + " " + ansprechpartner.getName());
        nameValue.setFont(Font.font("Verdana", 16));

        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, nameValue);

        Text projectsLabel = new Text("# Projekte:\t");
        projectsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Text projectsValue = new Text(String.valueOf(anzProjekte));
        projectsValue.setFont(Font.font("Verdana", 16));

        HBox projBox = new HBox();
        projBox.getChildren().addAll(projectsLabel, projectsValue);

        getChildren().addAll(nameBox, projBox);
    }

    public Ansprechpartner getAnsprechpartner() {
        return ansprechpartner;
    }

    public int getAnzProjekte() {
        return anzProjekte;
    }

    @Override
    public String toString() {
        return String.format(ansprechpartner.getVorname() + " " + ansprechpartner.getName());
    }
}
