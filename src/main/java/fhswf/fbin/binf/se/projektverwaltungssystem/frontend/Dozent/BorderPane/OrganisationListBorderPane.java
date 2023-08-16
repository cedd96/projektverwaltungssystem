package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox.OrganisationListOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.StudentOrganisationListOptionsHBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class OrganisationListBorderPane extends BorderPane {

    private OrganisationListViewVBox myListView = new OrganisationListViewVBox();

    public OrganisationListBorderPane() throws SQLException {
        if(LoginHandler.getCurrentUser() instanceof Dozent)
            setTop(new OrganisationListOptionsHBox(myListView));
        else if(LoginHandler.getCurrentUser() instanceof Student)
            setTop(new StudentOrganisationListOptionsHBox(myListView));
        setCenter(myListView);
    }
}
