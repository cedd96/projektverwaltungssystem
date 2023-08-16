package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupboardListViewItemVBoxComparator.TitelComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Antrag;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.GroupInfoBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.GridPane.LoginGridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class GroupboardListViewVBox extends VBox {

    private ListView<GroupboardListViewItemVBox> myListView;

    private SortedList<GroupboardListViewItemVBox> groupWidgets;

    public GroupboardListViewVBox() throws SQLException {
        myListView = new ListView<>();
        myListView.setId("myListView");
        myListView.setItems(getListData());
        myListView.setPlaceholder(new Label("Die Liste ist leer."));

        myListView.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                if(myListView.getSelectionModel().getSelectedItem() != null) {
                    Main.getPopupStage().setScene(new Scene(new GroupboardJoinPopupVBox(myListView.getSelectionModel().getSelectedItem(), this), 360, 160));
                    Main.getPopupStage().setTitle("Gruppe beitreten");
                    Main.getPopupStage().show();
                }
            }
        });

        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
        setVgrow(myListView, Priority.ALWAYS);
        getChildren().add(myListView);
    }

    public ObservableList<GroupboardListViewItemVBox> getListData() throws SQLException {
        ObservableList<GroupboardListViewItemVBox> dataList = FXCollections.observableArrayList();
        SortedList<GroupboardListViewItemVBox> sortedList = new SortedList<>(dataList);
        List<Gruppe> allGroups = Gruppe.getAllGroups(Main.getDB());
        Antrag antrag;

        for(Gruppe g : allGroups) {
            if(!g.containsMatrikelnummer(((Student) LoginHandler.getCurrentUser()).getMatrikelnummer())) {
                if(g.getMitglieder().size() < 3) {
                    antrag = Antrag.getAntragByGruppeOID(g.getGruppeOID());
                    if(antrag == null) {
                        dataList.add(new GroupboardListViewItemVBox(g));
                    }
                    else {
                        if(antrag.getStatus() == AntragStatus.FEHLT) {
                            dataList.add(new GroupboardListViewItemVBox(g));
                        }
                    }
                }
            }
        }

        sortedList.setComparator(new TitelComparator());

        return groupWidgets = sortedList;
    }

    public SortedList<GroupboardListViewItemVBox> getListItems() {
        return groupWidgets;
    }

    public ListView<GroupboardListViewItemVBox> getListView() {
        return myListView;
    }
}
