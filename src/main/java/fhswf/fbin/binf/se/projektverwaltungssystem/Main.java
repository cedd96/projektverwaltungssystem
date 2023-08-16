package fhswf.fbin.binf.se.projektverwaltungssystem;

import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;
import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane.LoginBorderPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    private static Double winWidth = Double.valueOf(1080);

    public static Double getWinWidth() {
        return winWidth;
    }

    private static Double winHeight = Double.valueOf(720);

    public static Double getWinHeight() {
        return winHeight;
    }

    private static Stage myStage;

    public static Stage getPrimaryStage() {
        return myStage;
    }

    private static Stage popupStage;

    public static Stage getPopupStage() {
        return popupStage;
    }

    private static String listStyle;

    public static String getListStyle() { return listStyle; }

    private static DatabaseConnection connection = null;

    public static DatabaseConnection getDB() {
        return connection;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            connection = new DatabaseConnection();

            myStage = primaryStage;
            popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            BorderPane root = new LoginBorderPane();
            Scene scene = new Scene(root, winWidth, winHeight);

            listStyle = getClass().getResource("/stylesheets/listStyle.css").toExternalForm();
            primaryStage.getIcons().add(new Image("https://cdn0.iconfinder.com/data/icons/flat-round-system/512/java-256.png"));
            popupStage.getIcons().add(new Image("https://cdn0.iconfinder.com/data/icons/flat-round-system/512/java-256.png"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Projektverwaltungssystem/Anmeldung");
            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}