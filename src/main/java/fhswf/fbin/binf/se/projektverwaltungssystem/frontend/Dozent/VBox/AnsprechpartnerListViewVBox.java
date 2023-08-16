package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.AnsprechpartnerListViewItemVBoxComparator.NameComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Ansprechpartner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AnsprechpartnerListViewVBox extends VBox {

    private ListView<AnsprechpartnerListViewItemVBox> myListView;

    private SortedList<AnsprechpartnerListViewItemVBox> ansprechpartnerWidgets;

    public AnsprechpartnerListViewVBox(OrganisationListViewItemVBox orgaWidget) throws SQLException {
        myListView = new ListView<>();
        myListView.setId("myListView");
        myListView.setItems(generateListViewItems(orgaWidget));
        myListView.setPlaceholder(new Label("Es wurde noch kein Ansprechpartner für das Unternehmen '" + orgaWidget.getOrganisation().getName() + "' angelegt."));

        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
        setVgrow(myListView, Priority.ALWAYS);
        getChildren().add(myListView);
    }

    public SortedList<AnsprechpartnerListViewItemVBox> generateListViewItems(OrganisationListViewItemVBox orgaWidget) throws SQLException {
        ObservableList<AnsprechpartnerListViewItemVBox> myItemList = FXCollections.observableArrayList();
        SortedList<AnsprechpartnerListViewItemVBox> mySortedList = new SortedList<>(myItemList);
        List<Ansprechpartner> allAnsprechpartner = Ansprechpartner.getAllAnsprechpartner(Main.getDB());

        for(Ansprechpartner a : allAnsprechpartner){
            if(Objects.equals(a.getOrganisation().getOrganisationOID(), orgaWidget.getOrganisation().getOrganisationOID()))
                myItemList.add(new AnsprechpartnerListViewItemVBox(a));
        }

        mySortedList.setComparator(new NameComparator());

        return ansprechpartnerWidgets = mySortedList;
    }

    public SortedList<AnsprechpartnerListViewItemVBox> getListItems() {
        return ansprechpartnerWidgets;
    }

    public ListView<AnsprechpartnerListViewItemVBox> getListView() {
        return myListView;
    }

    public void addAndSort(AnsprechpartnerListViewItemVBox ansprechpartnerWidget, Comparator<AnsprechpartnerListViewItemVBox> comparator) {
        ObservableList<AnsprechpartnerListViewItemVBox> unsortedList = FXCollections.observableArrayList();
        SortedList<AnsprechpartnerListViewItemVBox> sortedList = new SortedList<>(unsortedList);

        unsortedList.addAll(ansprechpartnerWidgets);
        unsortedList.add(ansprechpartnerWidget);
        sortedList.setComparator(comparator);

        ansprechpartnerWidgets = sortedList;
    }
}
