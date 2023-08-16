package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Dozent {

    private Integer dozentoid;

    private String name;

    private String vorname;

    public Dozent(String vorname, String name) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.dozentoid = autoIncDozentOID();
        save();
    }

    public Dozent(int dozentoid, String vorname, String name) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.dozentoid = dozentoid;
        save();
    }

    public Integer getDozentOID() {
        return dozentoid;
    }

    public void setDozentOID(Integer dozentoid) {
        this.dozentoid = dozentoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    private int autoIncDozentOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet dozentSet = statement.executeQuery("SELECT * FROM Dozent");
        int rowCount = dozentSet.last() ? dozentSet.getRow() : 0;

        dozentSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Dozent VALUES (%s, '%s', '%s') ON DUPLICATE KEY UPDATE Name = '%s', Vorname = '%s'",
                dozentoid,
                name,
                vorname,
                name,
                vorname));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Dozent> dozentList) throws SQLException {
        boolean saveCheck = false;

        for(Dozent d : dozentList)
            saveCheck = d.save();

        return saveCheck;
    }

    public static Dozent getDozentByOID(int dozentoid) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet dozentSet = statement.executeQuery("SELECT * FROM Dozent WHERE DozentOID = " + dozentoid);

        dozentSet.next();
        return new Dozent(dozentoid, dozentSet.getObject(2).toString(), dozentSet.getObject(3).toString());
    }

    public static List<Dozent> getAllDozenten(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet dozentSet = statement.executeQuery("SELECT * FROM Dozent");
        List<Dozent> allDozenten = new ArrayList<>();

        while(dozentSet.next()) {
            allDozenten.add(new Dozent(Integer.parseInt(dozentSet.getObject(1).toString()), dozentSet.getObject(2).toString(), dozentSet.getObject(3).toString()));
        }
        dozentSet.close();

        return allDozenten;
    }

    @Override
    public String toString() {
        if(name.length() <= 5)
            return String.format("DozentOID: %s\tName: %s\t\t\tVorname: %s", dozentoid, name, vorname);
        else
            return String.format("DozentOID: %s\tName: %s\t\tVorname: %s", dozentoid, name, vorname);
    }
}
