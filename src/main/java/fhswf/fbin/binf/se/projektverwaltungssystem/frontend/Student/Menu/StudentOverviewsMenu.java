package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.Menu;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.MenuItem.StudentGroupOverviewMenuItem;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.MenuItem.StudentOrganisationOverviewMenuItem;
import javafx.scene.control.Menu;

public class StudentOverviewsMenu extends Menu {
    private static final String MENUNAME = "_Übersicht";
    public StudentOverviewsMenu() {
        setText(MENUNAME);
        getItems().addAll(new StudentGroupOverviewMenuItem(), new StudentOrganisationOverviewMenuItem());
    }
}
