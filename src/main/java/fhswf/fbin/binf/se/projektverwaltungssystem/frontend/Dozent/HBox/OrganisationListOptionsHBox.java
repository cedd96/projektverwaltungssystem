package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.AnzAnsprechComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.AnzProjekteComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.NameComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Organisation;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.util.Comparator;

public class OrganisationListOptionsHBox extends HBox {

    private boolean sortOrder = true;

    private FilteredList<OrganisationListViewItemVBox> filteredList;

    private static final Comparator<OrganisationListViewItemVBox> defaultComparator = new NameComparator();

    public OrganisationListOptionsHBox(OrganisationListViewVBox myListView) {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10));
        filteredList = new FilteredList<>(myListView.getListItems(), b -> true);
        SortedList<OrganisationListViewItemVBox> sortedList = new SortedList<>(filteredList);
        sortedList.setComparator(defaultComparator);
        myListView.getListView().setItems(sortedList);

        //Combobox Options
        ObservableList<Comparator<OrganisationListViewItemVBox>> sortOptions = FXCollections.observableArrayList(
                new NameComparator(),
                new AnzAnsprechComparator(),
                new AnzProjekteComparator()
        );

        Image sortIcon = new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_asc-48.png");
        ImageView sortIconView = new ImageView(sortIcon);
        sortIconView.setFitWidth(32);
        sortIconView.setFitHeight(32);

        HBox sortIconViewHBox = new HBox();
        sortIconViewHBox.setId("sortBox");
        sortIconViewHBox.getStylesheets().add(getClass().getResource("/stylesheets/sortIconStyle.css").toExternalForm());
        sortIconViewHBox.getChildren().add(sortIconView);

        ComboBox<Comparator<OrganisationListViewItemVBox>> sortComboBox = new ComboBox<>();
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

        Button logoutButton = new Button("Abmelden");
        Region spacerRegion = new Region();
        setHgrow(spacerRegion, Priority.ALWAYS);

        sortComboBox.setOnAction(actionEvent -> {
            if (sortComboBox.getSelectionModel().getSelectedItem() instanceof NameComparator) {
                if (sortOrder) {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_asc-48.png"));
                    sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem());
                } else {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_desc-48.png"));
                    sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem().reversed());
                }
            } else if (sortComboBox.getSelectionModel().getSelectedItem() instanceof AnzProjekteComparator || sortComboBox.getSelectionModel().getSelectedItem() instanceof AnzAnsprechComparator) {
                if (sortOrder) {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_numeric_asc-48.png"));
                    sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem());
                } else {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_numeric_desc-48.png"));
                    sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem().reversed());
                }
            }

            myListView.getListView().setItems(sortedList);
        });

        sortIconViewHBox.setOnMouseClicked(mouseEvent -> {
            if (sortComboBox.getSelectionModel().getSelectedItem() != null) {
                if (sortComboBox.getSelectionModel().getSelectedItem() instanceof NameComparator) {
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
                } else if (sortComboBox.getSelectionModel().getSelectedItem() instanceof AnzProjekteComparator || sortComboBox.getSelectionModel().getSelectedItem() instanceof AnzAnsprechComparator) {
                    if (sortOrder) {
                        sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_numeric_desc-48.png"));
                        sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem().reversed());
                        myListView.getListView().setItems(sortedList);
                        sortOrder = false;
                    } else {
                        sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_numeric_asc-48.png"));
                        sortedList.setComparator(sortComboBox.getSelectionModel().getSelectedItem());
                        myListView.getListView().setItems(sortedList);
                        sortOrder = true;
                    }
                }
            } else {
                if (sortedList.getComparator().equals(defaultComparator)) {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_desc-48.png"));
                    sortedList.setComparator(defaultComparator.reversed());
                    myListView.getListView().setItems(sortedList);
                    sortOrder = false;
                } else if (sortedList.getComparator().equals(defaultComparator.reversed())) {
                    sortIconView.setImage(new Image("https://cdn0.iconfinder.com/data/icons/sort-2/20/sort_alpha_asc-48.png"));
                    sortedList.setComparator(defaultComparator);
                    myListView.getListView().setItems(sortedList);
                    sortOrder = true;
                }
            }
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(orgaWidget -> {
                if (newValue == null || newValue.isBlank())
                    return true;

                String search = newValue.toLowerCase();
                search = search.replaceAll("\\s", "");

                if (orgaWidget.getOrganisation().getName().toLowerCase().replaceAll("\\s", "").contains(search)) {
                    return true;
                } else if (String.valueOf(orgaWidget.getAnzAnsprechpartner()).toLowerCase().replaceAll("\\s", "").contains(search)) {
                    return true;
                } else if(String.valueOf(orgaWidget.getAnzProjekte()).toLowerCase().replaceAll("\\s", "").contains(search)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        logoutButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
        });

        getChildren().addAll(sortIconViewHBox, sortComboBox, searchIconLabel, searchInput, spacerRegion, logoutButton);
    }
}
