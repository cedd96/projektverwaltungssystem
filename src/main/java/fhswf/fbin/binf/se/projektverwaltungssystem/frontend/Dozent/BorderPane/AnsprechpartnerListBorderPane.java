package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox.AnsprechpartnerListOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.StudentAnsprechpartnerListOptionsHBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class AnsprechpartnerListBorderPane extends BorderPane {

    private AnsprechpartnerListViewVBox myListView;

    public AnsprechpartnerListBorderPane(OrganisationListViewItemVBox orgaWidget) throws SQLException {
        myListView = new AnsprechpartnerListViewVBox(orgaWidget);
        if(LoginHandler.getCurrentUser() instanceof Dozent)
            setTop(new AnsprechpartnerListOptionsHBox(orgaWidget, myListView));
        else if(LoginHandler.getCurrentUser() instanceof Student)
            setTop(new StudentAnsprechpartnerListOptionsHBox(orgaWidget, myListView));
        setCenter(myListView);
    }
}
