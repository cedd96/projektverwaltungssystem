package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox.GroupListOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewVBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class GroupListBorderPane extends BorderPane {

    private GroupListViewVBox myListView = new GroupListViewVBox();

    public GroupListBorderPane() throws SQLException {
        setTop(new GroupListOptionsHBox(myListView));
        setCenter(myListView);
    }
}
