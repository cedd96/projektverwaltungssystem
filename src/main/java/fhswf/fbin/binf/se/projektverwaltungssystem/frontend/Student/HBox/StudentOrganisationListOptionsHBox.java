package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.AnzAnsprechComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.AnzProjekteComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.OrganisationListViewItemVBoxComparator.NameComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Organisation;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.Comparator;

public class StudentOrganisationListOptionsHBox extends HBox {

    private boolean sortOrder = true;

    private FilteredList<OrganisationListViewItemVBox> filteredList;

    private static final Comparator<OrganisationListViewItemVBox> defaultComparator = new NameComparator();

    public StudentOrganisationListOptionsHBox(OrganisationListViewVBox myListView) {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10));
        ObservableList<OrganisationListViewItemVBox> observableList = FXCollections.observableArrayList();
        observableList.addAll(myListView.getListItems());
        filteredList = new FilteredList<>(observableList, b -> true);
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
        sortIconViewHBox.setAlignment(Pos.CENTER);
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

        Accordion accordion = new Accordion();

        TextField orgaName = new TextField();
        orgaName.setPromptText("Organisationsname...");
        orgaName.setFont(Font.font("Verdana", 12));

        Button addButton = new Button();
        Image addIcon = new Image("https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_add_48px-512.png");
        ImageView addIconView = new ImageView(addIcon);
        addIconView.setFitHeight(16);
        addIconView.setFitWidth(16);

        addButton.setGraphic(addIconView);

        HBox accordionInputsBox = new HBox(5);
        accordionInputsBox.getChildren().addAll(orgaName, addButton);

        TitledPane titledPane = new TitledPane("Organisation hinzufügen", accordionInputsBox);
        accordion.getPanes().add(titledPane);

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

        logoutButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
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

        addButton.setOnAction(actionEvent -> {
            try {
                OrganisationListViewItemVBox widget = new OrganisationListViewItemVBox(new Organisation(orgaName.getText()));
                observableList.add(widget);

                if(sortComboBox.getSelectionModel().getSelectedItem() == null) {
                    myListView.addAndSort(widget, new NameComparator());
                }
                else {
                    if(sortOrder) {
                        myListView.addAndSort(widget, sortComboBox.getSelectionModel().getSelectedItem().reversed());
                    }
                    else {
                        myListView.addAndSort(widget, sortComboBox.getSelectionModel().getSelectedItem());
                    }
                }
                myListView.getListView().setItems(myListView.getListItems());
                orgaName.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        getChildren().addAll(sortIconViewHBox, sortComboBox, searchIconLabel, searchInput, accordion, spacerRegion, logoutButton);
    }
}
