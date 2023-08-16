package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.StudentGroupListViewItemVBoxComparator;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;

import java.util.Comparator;

public class StatusComparator implements Comparator<StudentGroupListViewItemVBox> {
    public int compare(StudentGroupListViewItemVBox w1, StudentGroupListViewItemVBox w2){
        if(w1 != null && w2 != null) {
            if(w1.getAntrag() != null && w2.getAntrag() != null){
                return w1.getAntrag().getStatus().compareTo(w2.getAntrag().getStatus());
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
