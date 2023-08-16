package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.StudentGroupInfoOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.ScrollPane.StudentGroupInfoScrollPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class StudentGroupInfoScrollableBorderPane extends BorderPane {
    public StudentGroupInfoScrollableBorderPane(StudentGroupListViewItemVBox groupWidget) throws SQLException {
        setTop(new StudentGroupInfoOptionsHBox(groupWidget));
        setCenter(new StudentGroupInfoScrollPane(groupWidget));
    }
}
