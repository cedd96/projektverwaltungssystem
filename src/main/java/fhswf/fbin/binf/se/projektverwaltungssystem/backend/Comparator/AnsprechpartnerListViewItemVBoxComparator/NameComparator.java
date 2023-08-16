package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.AnsprechpartnerListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewItemVBox;

import java.util.Comparator;

public class NameComparator implements Comparator<AnsprechpartnerListViewItemVBox> {
    public int compare(AnsprechpartnerListViewItemVBox w1, AnsprechpartnerListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getAnsprechpartner() != null && w2.getAnsprechpartner() != null){
                return (w1.getAnsprechpartner().getVorname() + w1.getAnsprechpartner().getName()).compareToIgnoreCase(w2.getAnsprechpartner().getVorname() + w2.getAnsprechpartner().getName());
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Name";
    }
}