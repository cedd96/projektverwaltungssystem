package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;

import java.util.Comparator;

public class NameComparator implements Comparator<OrganisationListViewItemVBox> {
    public int compare(OrganisationListViewItemVBox w1, OrganisationListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getOrganisation() != null && w2.getOrganisation() != null){
                return w1.getOrganisation().getName().compareTo(w2.getOrganisation().getName());
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Name";
    }
}