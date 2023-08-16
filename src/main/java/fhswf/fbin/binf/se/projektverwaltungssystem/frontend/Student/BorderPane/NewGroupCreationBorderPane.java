package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox.NewGroupOptionsHBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.NewGroupContentVBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class NewGroupCreationBorderPane extends BorderPane {
    public NewGroupCreationBorderPane() throws SQLException {
        setTop(new NewGroupOptionsHBox());
        setCenter(new NewGroupContentVBox());
    }
}
