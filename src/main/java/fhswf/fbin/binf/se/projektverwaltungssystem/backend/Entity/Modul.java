package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Modul {

    private int moduloid;

    private String modulname;

    private Dozent dozent;

    public Modul(Dozent dozent, String modulname) throws SQLException {
        this.moduloid = autoIncModulOID();
        this.dozent = dozent;
        this.modulname = modulname;
        save();
    }

    public Modul(int moduloid, int dozentoid, String modulname) throws SQLException {
        this.moduloid = moduloid;
        this.dozent = findMatchingDozent(dozentoid);
        this.modulname = modulname;
        save();
    }

    public int getModulOID() {
        return moduloid;
    }

    public void setModulOID(int moduloid) {
        this.moduloid = moduloid;
    }

    public String getModulname() {
        return modulname;
    }

    public void setModulname(String modulname) {
        this.modulname = modulname;
    }

    public Dozent getDozent() {
        return dozent;
    }

    public void setDozent(Dozent dozent) throws SQLException {
        this.dozent = dozent;
        save();
    }

    public void setDozent(int dozentoid) throws SQLException {
        this.dozent = findMatchingDozent(dozentoid);
        save();
    }

    private Dozent findMatchingDozent(int dozentoid) throws SQLException {
        List<Dozent> dozentList = Dozent.getAllDozenten(Main.getDB());

        for(Dozent d : dozentList) {
            if(d.getDozentOID() == dozentoid)
                return d;
        }

        return null;
    }

    private int autoIncModulOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet modulSet = statement.executeQuery("SELECT * FROM Modul");
        int rowCount = modulSet.last() ? modulSet.getRow() : 0;

        modulSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Modul VALUES (%s, %s, '%s') ON DUPLICATE KEY UPDATE ModulOID = %s", moduloid, dozent.getDozentOID(), modulname, moduloid));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Modul> modulList) throws SQLException {
        boolean saveCheck = false;

        for(Modul m : modulList)
            saveCheck = m.save();

        return saveCheck;
    }

    public static List<Modul> getAllModule(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet modulSet = statement.executeQuery("SELECT * FROM Modul");
        List<Modul> allModule = new ArrayList<>();

        while(modulSet.next()) {
            allModule.add(new Modul(Integer.parseInt(modulSet.getObject(1).toString()), Integer.parseInt(modulSet.getObject(2).toString()), modulSet.getObject(3).toString()));
        }
        modulSet.close();

        return allModule;
    }

    @Override
    public String toString() {
        return String.format("%s", modulname);
    }
}
