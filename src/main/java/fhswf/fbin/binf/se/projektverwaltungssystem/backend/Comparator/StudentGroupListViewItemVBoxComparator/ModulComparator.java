package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.StudentGroupListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Comparator;

public class ModulComparator implements Comparator<StudentGroupListViewItemVBox> {
    public int compare(StudentGroupListViewItemVBox w1, StudentGroupListViewItemVBox w2) {
        if(w1 != null && w2 != null) {
            try {
                if(w1.getGruppe().getModul() != null && w2.getGruppe().getModul() != null){
                    return w1.getGruppe().getModul().getModulname().compareTo(w2.getGruppe().getModul().getModulname());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Modulname";
    }
}
