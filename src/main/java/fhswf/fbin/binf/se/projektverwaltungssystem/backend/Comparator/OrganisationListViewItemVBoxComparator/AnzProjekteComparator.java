package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;

import java.util.Comparator;

public class AnzProjekteComparator implements Comparator<OrganisationListViewItemVBox> {
    public int compare(OrganisationListViewItemVBox w1, OrganisationListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getOrganisation() != null && w2.getOrganisation() != null){
                return String.valueOf(w1.getAnzProjekte()).compareTo(String.valueOf(w2.getAnzProjekte()));
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Anz. Projekte";
    }
}