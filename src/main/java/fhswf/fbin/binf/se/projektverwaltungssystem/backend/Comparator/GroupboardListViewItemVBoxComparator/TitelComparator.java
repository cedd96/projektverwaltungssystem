package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupboardListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.GroupboardListViewItemVBox;

import java.util.Comparator;

public class TitelComparator implements Comparator<GroupboardListViewItemVBox> {
    public int compare(GroupboardListViewItemVBox w1, GroupboardListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getGruppe().getTitel() != null && w2.getGruppe().getTitel() != null){
                return w1.getGruppe().getTitel().compareTo(w2.getGruppe().getTitel());
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Gruppentitel";
    }
}