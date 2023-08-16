package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.StudentGroupListOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewVBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class StudentGroupListBorderPane extends BorderPane {

    private StudentGroupListViewVBox myListView = new StudentGroupListViewVBox();

    public StudentGroupListBorderPane() throws SQLException {
        setTop(new StudentGroupListOptionsHBox(myListView));
        setCenter(myListView);
    }
}
