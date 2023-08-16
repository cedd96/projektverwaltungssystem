package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organisation {
    private Integer organisationoid;

    private String name;

    public Organisation(String name) throws SQLException {
        this.organisationoid = autoIncOrganisationOID();
        this.name = name;
        save();
    }

    public Organisation(int organisationoid, String name) throws SQLException {
        this.organisationoid = organisationoid;
        this.name = name;
        save();
    }

    public Integer getOrganisationOID() {
        return organisationoid;
    }

    public void setOrganisationOID(Integer organisationoid) {
        this.organisationoid = organisationoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int autoIncOrganisationOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet organisationSet = statement.executeQuery("SELECT * FROM Organisation");
        int rowCount = organisationSet.last() ? organisationSet.getRow() : 0;

        organisationSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Organisation VALUES (%s, '%s') ON DUPLICATE KEY UPDATE Name = '%s'",
                organisationoid,
                name,
                name));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Organisation> organisationsList) throws SQLException {
        boolean saveCheck = false;

        for(Organisation o : organisationsList)
            saveCheck = o.save();

        return saveCheck;
    }

    public List<Antrag> getAllAntraege(List<Ansprechpartner> allAnsprechpartner) throws SQLException {
        List<Antrag> allAntraege = Antrag.getAllAntraege(Main.getDB());
        List<Antrag> allRelatedAntraege = new ArrayList<>();

        for(Antrag a : allAntraege) {
            for(Ansprechpartner ap : allAnsprechpartner) {
                if(Objects.equals(a.getAnsprechpartner().getAnsprechpartnerOID(), ap.getAnsprechpartnerOID())) {
                    if(a.getStatus() == AntragStatus.ZUGELASSEN)  {
                        allRelatedAntraege.add(a);
                        break;
                    }
                }
            }
        }
        return allRelatedAntraege;
    }

    public List<Ansprechpartner> getAllAnsprechpartner() throws SQLException {
        List<Ansprechpartner> allAnsprechpartner = Ansprechpartner.getAllAnsprechpartner(Main.getDB());

        allAnsprechpartner.removeIf(a -> !Objects.equals(a.getOrganisation().getOrganisationOID(), this.organisationoid));

        return allAnsprechpartner;
    }

    public static List<Organisation> getAllOrganisations(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet organisationsSet = statement.executeQuery("SELECT * FROM Organisation");
        List<Organisation> allOrganisations = new ArrayList<>();

        while(organisationsSet.next()) {
            allOrganisations.add(new Organisation(Integer.parseInt(organisationsSet.getObject(1).toString()), organisationsSet.getObject(2).toString()));
        }
        organisationsSet.close();

        return allOrganisations;
    }

    @Override
    public String toString() {
        return String.format("OrganisationOID: %s\tName: %s", organisationoid, name);
    }
}
