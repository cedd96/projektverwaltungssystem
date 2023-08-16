package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ansprechpartner {

    private Integer ansprechpartneroid;

    private Organisation organisation;

    private String name;

    private String vorname;

    public Ansprechpartner(String vorname, String name, Organisation organisation) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.organisation = organisation;
        this.ansprechpartneroid = autoIncAnsprechpartnerOID();
        save();
    }

    public Ansprechpartner(String vorname, String name, int organisationoid) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.organisation = findMatchingOrganisation(organisationoid);
        this.ansprechpartneroid = autoIncAnsprechpartnerOID();
        save();
    }

    public Ansprechpartner(int ansprechpartneroid, int organisationoid, String vorname, String name) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.ansprechpartneroid = ansprechpartneroid;
        this.organisation = findMatchingOrganisation(organisationoid);
        save();
    }

    public Integer getAnsprechpartnerOID() {
        return ansprechpartneroid;
    }

    public void setAnsprechpartnerOID(Integer ansprechpartneroid) {
        this.ansprechpartneroid = ansprechpartneroid;
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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) throws SQLException {
        this.organisation = organisation;
        save();
    }

    public void setOrganisation(int organisationoid) throws SQLException {
        this.organisation = findMatchingOrganisation(organisationoid);
        save();
    }

    private Organisation findMatchingOrganisation(int organisationoid) throws SQLException {
        List<Organisation> organisationsList = Organisation.getAllOrganisations(Main.getDB());

        for(Organisation o : organisationsList) {
            if(o.getOrganisationOID() == organisationoid)
                return o;
        }

        return null;
    }

    private int autoIncAnsprechpartnerOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet ansprechpartnerSet = statement.executeQuery("SELECT * FROM Ansprechpartner");
        int rowCount = ansprechpartnerSet.last() ? ansprechpartnerSet.getRow() : 0;

        ansprechpartnerSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Ansprechpartner VALUES (%s, %s, '%s', '%s') ON DUPLICATE KEY UPDATE OrganisationOID = %s, Vorname = '%s', Name = '%s'",
                ansprechpartneroid,
                organisation.getOrganisationOID(),
                vorname,
                name,
                organisation.getOrganisationOID(),
                vorname,
                name));

        if(affectedRows > 0)
            return true;
        else
            return false;
    }

    public static boolean saveAll(List<Ansprechpartner> ansprechpartnerList) throws SQLException {
        boolean saveCheck = false;

        for(Ansprechpartner a : ansprechpartnerList)
            saveCheck = a.save();

        if(saveCheck)
            return true;
        else
            return false;
    }

    public int getProjectCount() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet antragSet = statement.executeQuery("SELECT AnsprechpartnerOID, Status FROM Antrag");
        int counter = 0;

        while(antragSet.next()) {
            if(antragSet.getInt("AnsprechpartnerOID") == this.ansprechpartneroid && antragSet.getString("Status").equals(AntragStatus.ZUGELASSEN.getBeschreibung()))
                counter++;
        }

        return counter;
    }

    public static List<Ansprechpartner> getAllAnsprechpartner(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet ansprechpartnerSet = statement.executeQuery("SELECT * FROM Ansprechpartner");
        List<Ansprechpartner> allAnsprechpartner = new ArrayList<>();

        while(ansprechpartnerSet.next()) {
            allAnsprechpartner.add(new Ansprechpartner(Integer.parseInt(ansprechpartnerSet.getObject(1).toString()), Integer.parseInt(ansprechpartnerSet.getObject(2).toString()), ansprechpartnerSet.getObject(3).toString(), ansprechpartnerSet.getObject(4).toString()));
        }
        ansprechpartnerSet.close();

        return allAnsprechpartner;
    }

    @Override
    public String toString() {
        return String.format("%s %s", vorname, name);
    }
}
