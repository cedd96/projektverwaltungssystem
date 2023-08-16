package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.ScrollPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupInfoContentVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupInfoContentVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

import java.sql.SQLException;

public class StudentGroupInfoScrollPane extends ScrollPane {

    private static final String SCROLLSTYLE = "-fx-background-color: transparent;";

    public StudentGroupInfoScrollPane(StudentGroupListViewItemVBox groupWidget) throws SQLException {
        setContent(new StudentGroupInfoContentVBox(groupWidget));
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setStyle(SCROLLSTYLE);
        setPadding(new Insets(0, 100, 0, 100));
        setFitToWidth(true);
    }
}
