package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;

import java.util.Comparator;

public class AnzAnsprechComparator implements Comparator<OrganisationListViewItemVBox> {
    public int compare(OrganisationListViewItemVBox w1, OrganisationListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getOrganisation() != null && w2.getOrganisation() != null){
                return String.valueOf(w1.getAnzAnsprechpartner()).compareTo(String.valueOf(w2.getAnzAnsprechpartner()));
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Anz. Ansprechpartner";
    }
}