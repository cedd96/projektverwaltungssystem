package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Student.HBox;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.AnsprechpartnerListViewItemVBoxComparator.AnzProjekteComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Comparator.AnsprechpartnerListViewItemVBoxComparator.NameComparator;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Ansprechpartner;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.LoginHandler;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.BorderPane.OrganisationOverviewBorderPane;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewItemVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.AnsprechpartnerListViewVBox;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Dozent.VBox.OrganisationListViewItemVBox;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentAnsprechpartnerListOptionsHBox extends HBox {

    private boolean sortOrder = true;

    private FilteredList<AnsprechpartnerListViewItemVBox> filteredList;

    private static final Comparator<AnsprechpartnerListViewItemVBox> defaultComparator = new NameComparator();

    public StudentAnsprechpartnerListOptionsHBox(OrganisationListViewItemVBox orgaWidget, AnsprechpartnerListViewVBox myListView) {
        setSpacing(10);
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(10));
        ObservableList<AnsprechpartnerListViewItemVBox> observableList = FXCollections.observableArrayList();
        observableList.addAll(myListView.getListItems());
        filteredList = new FilteredList<>(observableList, b -> true);
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
        sortIconViewHBox.setAlignment(Pos.CENTER);
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

        Accordion accordion = new Accordion();

        TextField ansprName = new TextField();
        ansprName.setPrefWidth(100);
        ansprName.setPromptText("Nachname...");
        ansprName.setFont(Font.font("Verdana", 12));

        TextField ansprVorname = new TextField();
        ansprVorname.setPrefWidth(100);
        ansprVorname.setPromptText("Vorname...");
        ansprVorname.setFont(Font.font("Verdana", 12));

        Button addButton = new Button();
        Image addIcon = new Image("https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_add_48px-512.png");
        ImageView addIconView = new ImageView(addIcon);
        addIconView.setFitHeight(16);
        addIconView.setFitWidth(16);

        addButton.setGraphic(addIconView);


        HBox accordionInputsBox = new HBox(5);
        accordionInputsBox.getChildren().addAll(ansprVorname, ansprName, addButton);

        TitledPane titledPane = new TitledPane("Ansprechpartner hinzufügen", accordionInputsBox);
        accordion.getPanes().add(titledPane);

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
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        logoutButton.setOnAction(actionEvent -> {
            Main.getPrimaryStage().setScene(new Scene(new LoginBorderPane(), Main.getWinWidth(), Main.getWinHeight()));
            Main.getPrimaryStage().setTitle("Projektverwaltungssystem/Anmeldung");
        });

        addButton.setOnAction(actionEvent -> {
            try {
                if(firstName(ansprVorname.getText()) && lastName(ansprName.getText())) {
                    AnsprechpartnerListViewItemVBox widget = new AnsprechpartnerListViewItemVBox(new Ansprechpartner(firstLetterToUpperCase(ansprVorname.getText()), firstLetterToUpperCase(ansprName.getText()), orgaWidget.getOrganisation()));
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

                    ansprVorname.setStyle("");
                    ansprName.setStyle("");
                }
                else {
                    if(!firstName(ansprVorname.getText())) {
                        ansprVorname.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: red;");
                    }
                    else {
                        ansprVorname.setStyle("");
                    }

                    if(!lastName(ansprName.getText())) {
                        ansprName.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: red;");
                    }
                    else {
                        ansprName.setStyle("");
                    }
                }
                myListView.getListView().setItems(myListView.getListItems());
                ansprVorname.clear();
                ansprName.clear();
            } catch (SQLException er) {
                er.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + er.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        getChildren().addAll(backButton, sortIconViewHBox, sortComboBox, searchIconLabel, searchInput, accordion, spacerRegion, logoutButton);
    }

    private static boolean firstName( String firstName ) {
        return firstName.matches("[a-zA-Z][a-zA-Z]*");
    }

    private static boolean lastName( String lastName ) {
        return lastName.matches("[a-zA-Z]+([ '-][a-zA-Z]+)*");
    }

    private String firstLetterToUpperCase(String stringToChange) {
        String firstLetter = stringToChange.substring(0, 1).toUpperCase();
        String remainingString = stringToChange.substring(1).toLowerCase();

        return firstLetter + remainingString;
    }
}
