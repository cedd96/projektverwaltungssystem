package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.ScrollPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupInfoContentVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

import java.sql.SQLException;

public class GroupInfoScrollPane extends ScrollPane {

    private static final String SCROLLSTYLE = "-fx-background-color: transparent;";

    public GroupInfoScrollPane(GroupListViewItemVBox groupWidget) throws SQLException {
        setContent(new GroupInfoContentVBox(groupWidget));
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setStyle(SCROLLSTYLE);
        setPadding(new Insets(0, 100, 0, 100));
        setFitToWidth(true);
    }

}
