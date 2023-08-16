package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;

import java.util.Comparator;

public class TitelComparator implements Comparator<GroupListViewItemVBox> {
    public int compare(GroupListViewItemVBox w1, GroupListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getGruppe().getTitel() != null && w2.getGruppe().getTitel() != null){
                return w1.getGruppe().getTitel().compareToIgnoreCase(w2.getGruppe().getTitel());
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Gruppentitel";
    }
}