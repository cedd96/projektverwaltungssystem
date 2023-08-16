package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.MenuBar;


import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.Menu.StudentOverviewsMenu;
import javafx.scene.control.MenuBar;

public class StudentPrimaryMenuBar extends MenuBar {
    public StudentPrimaryMenuBar() {
        getMenus().add(new StudentOverviewsMenu());
    }
}
