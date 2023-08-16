package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.MenuBar.PrimaryMenuBar;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.MenuBar.StudentPrimaryMenuBar;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class StudentGroupInfoBorderPane extends BorderPane {
    public StudentGroupInfoBorderPane(StudentGroupListViewItemVBox groupWidget) throws SQLException {
        if(LoginHandler.getCurrentUser() instanceof Dozent)
            setTop(new PrimaryMenuBar());
        else if(LoginHandler.getCurrentUser() instanceof Student)
            setTop(new StudentPrimaryMenuBar());
        setCenter(new StudentGroupInfoScrollableBorderPane(groupWidget));
    }
}
