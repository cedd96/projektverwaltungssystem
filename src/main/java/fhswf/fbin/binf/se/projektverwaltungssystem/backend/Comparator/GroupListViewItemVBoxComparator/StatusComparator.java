package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;

import java.util.Comparator;

public class StatusComparator implements Comparator<GroupListViewItemVBox> {
    public int compare(GroupListViewItemVBox w1, GroupListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getAntrag() != null && w2.getAntrag() != null){
                return w1.getAntrag().getStatus().getBeschreibung().compareToIgnoreCase(w2.getAntrag().getStatus().getBeschreibung());
            }
            else
                return (w1.getAntrag() == null ? -1 : 1);
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Status";
    }
}
