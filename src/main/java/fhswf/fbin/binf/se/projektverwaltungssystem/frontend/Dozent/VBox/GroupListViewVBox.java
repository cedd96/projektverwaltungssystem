package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupListViewItemVBoxComparator.TitelComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.GroupInfoBorderPane;
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
import java.util.List;
import java.util.Objects;

public class GroupListViewVBox extends VBox {

    private ListView<GroupListViewItemVBox> myListView;

    private SortedList<GroupListViewItemVBox> groupWidgets;

    public GroupListViewVBox() throws SQLException {
        myListView = new ListView<>();
        myListView.setId("myListView");
        myListView.setItems(generateListViewItems());
        myListView.setPlaceholder(new Label("Für ihre Module wurde noch keine Gruppe erstellt."));

        myListView.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                try {
                    if(myListView.getSelectionModel().getSelectedItem() != null) {
                        Main.getPrimaryStage().setScene(new Scene(new GroupInfoBorderPane(myListView.getSelectionModel().getSelectedItem()), Main.getWinWidth(), Main.getWinHeight()));
                        Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Gruppenübersicht/" + myListView.getSelectionModel().getSelectedItem().getGruppe().getTitel() + "/Info");
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

    public SortedList<GroupListViewItemVBox> generateListViewItems() throws SQLException {
        ObservableList<GroupListViewItemVBox> myItemList = FXCollections.observableArrayList();
        SortedList<GroupListViewItemVBox> mySortedList = new SortedList<>(myItemList);
        List<Gruppe> allGroups = Gruppe.getAllGroups(Main.getDB());

        for(Gruppe g : allGroups){
            if(Objects.equals(g.getModul().getDozent().getDozentOID(), ((Dozent) LoginHandler.getCurrentUser()).getDozentOID()))
                myItemList.add(new GroupListViewItemVBox(g));
        }

        mySortedList.setComparator(new TitelComparator());

        return groupWidgets = mySortedList;
    }

    public ListView<GroupListViewItemVBox> getListView() {
        return myListView;
    }

    public ObservableList<GroupListViewItemVBox> getListItems() {
        return groupWidgets;
    }
}
