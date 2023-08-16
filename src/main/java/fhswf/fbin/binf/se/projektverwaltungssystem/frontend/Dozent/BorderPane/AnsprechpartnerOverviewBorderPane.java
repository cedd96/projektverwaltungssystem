package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.MenuBar.PrimaryMenuBar;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.GridPane.LoginGridPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.MenuBar.StudentPrimaryMenuBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class AnsprechpartnerOverviewBorderPane extends BorderPane {
    public AnsprechpartnerOverviewBorderPane(OrganisationListViewItemVBox orgaWidget) throws SQLException {
        if(LoginHandler.getCurrentUser() instanceof Dozent)
            setTop(new PrimaryMenuBar());
        else if(LoginHandler.getCurrentUser() instanceof Student)
            setTop(new StudentPrimaryMenuBar());
        setCenter(new AnsprechpartnerListBorderPane(orgaWidget));
    }
}
