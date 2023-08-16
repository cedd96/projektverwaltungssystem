package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox.GroupInfoOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.ScrollPane.GroupInfoScrollPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class GroupInfoScrollableBorderPane extends BorderPane {
    public GroupInfoScrollableBorderPane(GroupListViewItemVBox groupWidget) throws SQLException {
        setTop(new GroupInfoOptionsHBox(groupWidget));
        setCenter(new GroupInfoScrollPane(groupWidget));
    }
}
