package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.GroupboardOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.GroupboardListViewVBox;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class GroupboardListBorderPane extends BorderPane {

    private GroupboardListViewVBox myListView = new GroupboardListViewVBox();

    public GroupboardListBorderPane() throws SQLException {
        setTop(new GroupboardOptionsHBox(myListView));
        setCenter(myListView);
    }
}
