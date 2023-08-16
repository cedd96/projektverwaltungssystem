package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.NameComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Organisation;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.AnsprechpartnerOverviewBorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class OrganisationListViewVBox extends VBox {

    private ListView<OrganisationListViewItemVBox> myListView;

    private SortedList<OrganisationListViewItemVBox> orgaWidgets;

    public OrganisationListViewVBox() throws SQLException {
        myListView = new ListView<>();
        myListView.setId("myListView");
        myListView.setItems(generateListViewItems());
        myListView.setPlaceholder(new Label("Es wurde noch keine Organisation angelegt."));

        myListView.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                try {
                    if(myListView.getSelectionModel().getSelectedItem() != null) {
                        Main.getPrimaryStage().setScene(new Scene(new AnsprechpartnerOverviewBorderPane(myListView.getSelectionModel().getSelectedItem()), Main.getWinWidth(), Main.getWinHeight()));
                        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                        Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Organisationen/" + myListView.getSelectionModel().getSelectedItem().getOrganisation().getName() + "/Ansprechpartner");
                    }
                } catch (SQLException er) {
                    er.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                            ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
        setVgrow(myListView, Priority.ALWAYS);
        getChildren().add(myListView);
    }

    public SortedList<OrganisationListViewItemVBox> generateListViewItems() throws SQLException {
        ObservableList<OrganisationListViewItemVBox> myItemList = FXCollections.observableArrayList();
        SortedList<OrganisationListViewItemVBox> sortedList = new SortedList<>(myItemList);
        List<Organisation> allOrganisations = Organisation.getAllOrganisations(Main.getDB());

        for(Organisation o : allOrganisations){
            myItemList.add(new OrganisationListViewItemVBox(o));
        }

        sortedList.setComparator(new NameComparator());
        return orgaWidgets = sortedList;
    }

    public SortedList<OrganisationListViewItemVBox> getListItems() {
        return orgaWidgets;
    }

    public ListView<OrganisationListViewItemVBox> getListView() {
        return myListView;
    }

    public void addAndSort(OrganisationListViewItemVBox orgaWidget, Comparator<OrganisationListViewItemVBox> comparator) {
        ObservableList<OrganisationListViewItemVBox> unsortedList = FXCollections.observableArrayList();
        SortedList<OrganisationListViewItemVBox> sortedList = new SortedList<>(unsortedList);

        unsortedList.addAll(orgaWidgets);
        unsortedList.add(orgaWidget);
        sortedList.setComparator(comparator);

        orgaWidgets = sortedList;
    }
}
