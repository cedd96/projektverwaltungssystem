package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.MenuBar;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.Menu.OverviewsMenu;
import javafx.scene.control.MenuBar;

public class PrimaryMenuBar extends MenuBar {
    public PrimaryMenuBar(){
        getMenus().add(new OverviewsMenu());
    }
}
