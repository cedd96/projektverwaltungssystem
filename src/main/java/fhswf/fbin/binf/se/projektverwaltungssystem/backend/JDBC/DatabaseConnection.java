package fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;

public class DatabaseConnection {

    private Connection con = null;

    public DatabaseConnection() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/projektverwaltung", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "SQLException: " + e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public Connection getConnection() {
        if (con == null)
            return new DatabaseConnection().getConnection();
        else
            return con;
    }
}
