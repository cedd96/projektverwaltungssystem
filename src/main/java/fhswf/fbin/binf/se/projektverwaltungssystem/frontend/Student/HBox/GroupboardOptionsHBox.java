package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupboardListViewItemVBoxComparator.ModulComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.GroupboardListViewItemVBoxComparator.TitelComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.GroupboardBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.GroupboardListBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.BorderPane.StudentGroupOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.GroupboardListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.GroupboardListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.VBox.StudentGroupListViewItemVBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.Comparator;

public class GroupboardOptionsHBox extends HBox {

    private static Label confirmation = new Label();

    private boolean sortOrder = true;

    private FilteredList<GroupboardListViewItemVBox> filteredList;

    private static final Comparator<GroupboardListViewItemVBox> defaultComparator = new TitelComparator();

    public GroupboardOptionsHBox(GroupboardListViewVBox myListView) {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10));
        filteredList = new FilteredList<>(myListView.getListItems(), b -> true);
        SortedList<GroupboardListViewItemVBox> sortedList = new SortedList<>(filteredList);
        sortedList.setComparator(defaultComparator);
        myListView.getListView().setItems(sortedList);

        Button logoutButton = new Button("Abmelden");

        Button backButton = new Button("Zurück");

        //Combobox Options
        ObservableList<Comparator<GroupboardListViewItemVBox>> sortOptions = FXCollections.observableArrayList(
                new TitelComparator(),
                new ModulComparator()
        );

        Image sortIcon = new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_asc-48.png");
        ImageView sortIconView = new ImageView(sortIcon);
        sortIconView.setFitWidth(32);
        sortIconView.setFitHeight(32);

        HBox sortIconViewHBox = new HBox();
        sortIconViewHBox.setId("sortBox");
        sortIconViewHBox.getStylesheets().add(getClass().getResource("/stylesheets/sortIconStyle.css").toExternalForm());
        sortIconViewHBox.getChildren().add(sortIconView);

        ComboBox<Comparator<GroupboardListViewItemVBox>> sortComboBox = new ComboBox<>();
        sortComboBox.setPromptText("Sortieren nach...");
        sortComboBox.setItems(sortOptions);

        Image searchIcon = new Image("https://cdn3.iconfinder.com/data/icons/linecons-free-vector-icons-pack/32/search-48.png");
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.setFitWidth(24);
        searchIconView.setFitHeight(24);

        Label searchIconLabel = new Label();
        searchIconLabel.setGraphic(searchIconView);
        searchIconLabel.setPadding(new Insets(0, 0, 0, 30));

        TextField searchInput = new TextField();
        searchInput.setFont(Font.font("Verdana", 12));
        searchInput.setPromptText("Suchen nach...");

        confirmation.setWrapText(true);

        Region spacer = new Region();
        setHgrow(spacer, Priority.ALWAYS);

        sortComboBox.setOnAction(actionEvent -> {
            if(sortOrder)
                sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem());
            else
                sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem().reversed());

            myListView.getListView().setItems(sortedList);
        });

        sortIconViewHBox.setOnMouseClicked(mouseEvent -> {
            if(sortComboBox.getSelectionModel().getSelectedItem() != null) {
                if (sortOrder) {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_desc-48.png"));
                    sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem().reversed());
                    myListView.getListView().setItems(sortedList);
                    sortOrder = false;
                } else {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_asc-48.png"));
                    sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem());
                    myListView.getListView().setItems(sortedList);
                    sortOrder = true;
                }
            }
            else if(sortedList.getComparator().equals(defaultComparator)) {
                sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_desc-48.png"));
                sortedList.setComparator(defaultComparator.reversed());
                myListView.getListView().setItems(sortedList);
                sortOrder = false;
            }
            else if(sortedList.getComparator().equals(defaultComparator.reversed())) {
                sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_asc-48.png"));
                sortedList.setComparator(defaultComparator);
                myListView.getListView().setItems(sortedList);
                sortOrder = true;
            }
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(groupWidget -> {
                if(newValue == null || newValue.isBlank())
                    return true;

                String search = newValue.toLowerCase();
                search = search.replaceAll("\\s", "");

                try {
                    if(groupWidget.getGruppe().getTitel().toLowerCase().replaceAll("\\s", "").contains(search)) {
                        return true;
                    }
                    else if(groupWidget.getGruppe().getModul().getModulname().toLowerCase().replaceAll("\\s", "").contains(search)) {
                        return true;
                    }
                    else if(groupWidget.getGruppe().getMitglieder().size() > 0) {
                        for(Student s : groupWidget.getGruppe().getMitglieder()) {
                            if((s.getVorname().toLowerCase() + s.getName().toLowerCase()).replaceAll("\\s", "").contains(search)) {
                                return true;
                            }
                        }
                    }
                    else {
                        return false;
                    }
                } catch (SQLException er) {
                    er.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                            ButtonType.OK);
                    alert.showAndWait();
                }
                return false;
            });
        });

        logoutButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
            resetConfirmationText();
        });

        backButton.setOnAction(actionEvent -> {
            try {
                Main.getPrimaryStage().setScene(new Scene(new StudentGroupOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Gruppenforum");
                resetConfirmationText();
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        getChildren().addAll(backButton, sortIconViewHBox, sortComboBox, searchIconLabel, searchInput, confirmation, spacer, logoutButton);
    }

    public static void setConfirmationText(String confText) {
        confirmation.setText(confText);
        confirmation.setTextFill(Color.GREEN);
    }

    public static void resetConfirmationText() {
        confirmation = new Label();
    }
}
