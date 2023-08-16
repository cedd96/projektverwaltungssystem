package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.AnsprechpartnerListViewItemVBoxComparator.AnzProjekteComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.AnsprechpartnerListViewItemVBoxComparator.NameComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.OrganisationOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.GroupListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Locale;

public class AnsprechpartnerListOptionsHBox extends HBox {

    private boolean sortOrder = true;

    private FilteredList<AnsprechpartnerListViewItemVBox> filteredList;

    private static final Comparator<AnsprechpartnerListViewItemVBox> defaultComparator = new NameComparator();

    public AnsprechpartnerListOptionsHBox(OrganisationListViewItemVBox orgaWidget, AnsprechpartnerListViewVBox myListView) {
        setSpacing(10);
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(10));
        filteredList = new FilteredList<>(myListView.getListItems(), b -> true);
        SortedList<AnsprechpartnerListViewItemVBox> sortedList = new SortedList<>(filteredList);
        sortedList.setComparator(defaultComparator);
        myListView.getListView().setItems(sortedList);

        //Combobox Options
        ObservableList<Comparator<AnsprechpartnerListViewItemVBox>> sortOptions = FXCollections.observableArrayList(
                new NameComparator(),
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

        ComboBox<Comparator<AnsprechpartnerListViewItemVBox>> sortComboBox = new ComboBox<>();
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

        Button backButton = new Button("Zurück");

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
            } else if (sortComboBox.getSelectionModel().getSelectedItem() instanceof AnzProjekteComparator) {
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
                } else if (sortComboBox.getSelectionModel().getSelectedItem() instanceof AnzProjekteComparator) {
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
            filteredList.setPredicate(ansprechpartnerWidget -> {
                if (newValue == null || newValue.isBlank())
                    return true;

                String search = newValue.toLowerCase();
                search = search.replaceAll("\\s", "");

                if ((ansprechpartnerWidget.getAnsprechpartner().getVorname().toLowerCase() + ansprechpartnerWidget.getAnsprechpartner().getName().toLowerCase()).replaceAll("\\s", "").contains(search)) {
                    return true;
                } else if ((String.valueOf(ansprechpartnerWidget.getAnzProjekte())).toLowerCase().replaceAll("\\s", "").contains(search)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        backButton.setOnAction(actionEvent -> {
            try {
                Main.getPrimaryStage().setScene(new Scene(new OrganisationOverviewBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
                Main.getPrimaryStage().getScene().getStylesheets().add(Main.getListStyle());
                Main.getPrimaryStage().setTitle("Projektverwaltungssystem/" + LoginHandler.getCurrentUserIdentifier() + "/Organisationen");
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        logoutButton.setOnAction(actionEvent -> {
            LoginHandler.setCurrentUser(null);
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
        });

        getChildren().addAll(backButton, sortIconViewHBox, sortComboBox, searchIconLabel, searchInput, spacerRegion, logoutButton);
    }
}
