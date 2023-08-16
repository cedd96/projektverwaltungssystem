package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.Menu;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.MenuItem.GroupOverviewMenuItem;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.MenuItem.OrganisationOverviewMenuItem;
import javafx.scene.control.Menu;

public class OverviewsMenu extends Menu {
    private static final String MENUNAME = "_Übersicht";
    public OverviewsMenu() {
        setText(MENUNAME);
        getItems().addAll(new GroupOverviewMenuItem(), new OrganisationOverviewMenuItem());
    }
}
