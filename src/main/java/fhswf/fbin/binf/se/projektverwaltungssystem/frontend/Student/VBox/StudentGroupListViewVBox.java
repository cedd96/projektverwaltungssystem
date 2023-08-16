package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.StudentGroupListViewItemVBoxComparator.TitelComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Gruppe;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.StudentGroupInfoBorderPane;
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

public class StudentGroupListViewVBox extends VBox {

    private ListView<StudentGroupListViewItemVBox> myListView;

    private SortedList<StudentGroupListViewItemVBox> groupWidgets;

    public StudentGroupListViewVBox() throws SQLException {
        myListView = new ListView<>();
        myListView.setId("myListView");
        myListView.setItems(generateListViewItems());
        myListView.setPlaceholder(new Label("Sie sind noch keiner Projektgruppe beigetreten.\nErstellen sie eine neue Gruppe über den Button 'Neue Gruppe'\noder treten sie einer bestehenden Gruppe über das 'Gruppenforum' bei."));

        myListView.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                try {
                    if(myListView.getSelectionModel().getSelectedItem() != null) {
                        Main.getPrimaryStage().setScene(new Scene(new StudentGroupInfoBorderPane(myListView.getSelectionModel().getSelectedItem()), Main.getWinWidth(), Main.getWinHeight()));
                        Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                        Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Gruppenübersicht/" + myListView.getSelectionModel().getSelectedItem().getGruppe().getTitel());
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

    public ObservableList<StudentGroupListViewItemVBox> generateListViewItems() throws SQLException {
        ObservableList<StudentGroupListViewItemVBox> myItemList = FXCollections.observableArrayList();
        SortedList<StudentGroupListViewItemVBox> sortedList = new SortedList<>(myItemList);

        Student currentUser = (Student) LoginHandler.getCurrentUser();
        List<Gruppe> allGroups = Gruppe.getAllGroupsForStudent(currentUser);

        for(Gruppe g : allGroups){
            myItemList.add(new StudentGroupListViewItemVBox(g));
        }

        sortedList.setComparator(new TitelComparator());
        return groupWidgets = sortedList;
    }

    public SortedList<StudentGroupListViewItemVBox> getListItems() {
        return groupWidgets;
    }

    public ListView<StudentGroupListViewItemVBox> getListView() {
        return myListView;
    }
}
