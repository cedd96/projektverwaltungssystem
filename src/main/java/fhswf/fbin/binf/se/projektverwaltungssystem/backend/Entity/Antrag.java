package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums.AntragStatus;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static java.sql.JDBCType.NULL;

public class Antrag {

    private Integer antragoid;

    private Gruppe gruppe;

    private Ansprechpartner ansprechpartner;

    private String titel;

    private String skizze;

    private String beschreibungKurz;

    private String beschreibungDetails;

    private AntragStatus status;

    private String kommentar;

    public Antrag(Gruppe gruppe, Ansprechpartner ansprechpartner, String titel, String skizze, String beschreibungKurz, String beschreibungDetails, String status) throws SQLException {
        this.antragoid = autoIncAntragOID();
        this.gruppe = gruppe;
        this.ansprechpartner = ansprechpartner;
        this.titel = titel;
        this.skizze = skizze;
        this.beschreibungKurz = beschreibungKurz;
        this.beschreibungDetails = beschreibungDetails;

        for(AntragStatus as : AntragStatus.values()) {
            if(as.getBeschreibung().equals(status))
                this.status = as;
        }
        save();
    }

    public Antrag(Gruppe gruppe, Ansprechpartner ansprechpartner, String titel, String skizze, String beschreibungKurz, String beschreibungDetails, AntragStatus status) throws SQLException {
        this.antragoid = autoIncAntragOID();
        this.gruppe = gruppe;
        this.ansprechpartner = ansprechpartner;
        this.titel = titel;
        this.skizze = skizze;
        this.beschreibungKurz = beschreibungKurz;
        this.beschreibungDetails = beschreibungDetails;
        this.status = status;
        save();
    }

    public Antrag(int antragoid, int gruppeoid, int ansprechpartneroid, String titel, String skizze, String beschreibungKurz, String beschreibungDetails, String kommentar, String status) throws SQLException {
        this.antragoid = antragoid;
        this.gruppe = findMatchingGroups(gruppeoid);
        this.ansprechpartner = findMatchingAnsprechpartner(ansprechpartneroid);
        this.titel = titel;
        this.skizze = skizze;
        this.beschreibungKurz = beschreibungKurz;
        this.beschreibungDetails = beschreibungDetails;

        for(AntragStatus as : AntragStatus.values()) {
            if(as.getBeschreibung().equals(status))
                this.status = as;
        }

        this.kommentar = kommentar;
    }

    public Integer getAntragoid() {
        return antragoid;
    }

    public void setAntragoid(Integer antragoid) {
        this.antragoid = antragoid;
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) throws SQLException {
        this.gruppe = gruppe;
        save();
    }

    public void setGruppe(int gruppeoid) throws SQLException {
        this.gruppe = findMatchingGroups(gruppeoid);
        save();
    }

    public Ansprechpartner getAnsprechpartner() {
        return ansprechpartner;
    }

    public void setAnsprechpartner(Ansprechpartner ansprechpartner) throws SQLException {
        this.ansprechpartner = ansprechpartner;
        save();
    }

    public void setAnsprechpartner(int ansprechpartneroid) throws SQLException {
        this.ansprechpartner = findMatchingAnsprechpartner(ansprechpartneroid);
        save();
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) throws SQLException {
        this.titel = titel;
        save();
    }

    public String getSkizze() {
        return skizze;
    }

    public void setSkizze(String skizze) throws SQLException {
        this.skizze = skizze;
        save();
    }

    public String getBeschreibungKurz() {
        return beschreibungKurz;
    }

    public void setBeschreibungKurz(String beschreibungKurz) throws SQLException {
        this.beschreibungKurz = beschreibungKurz;
        save();
    }

    public String getBeschreibungDetails() {
        return beschreibungDetails;
    }

    public void setBeschreibungDetails(String beschreibungDetails) throws SQLException {
        this.beschreibungDetails = beschreibungDetails;
        save();
    }

    public AntragStatus getStatus() {
        return status;
    }

    public void setStatus(AntragStatus status) throws SQLException {
        this.status = status;
        save();
    }

    public void setStatus(String beschreibung) throws SQLException {
        for(AntragStatus as : AntragStatus.values()) {
            if(as.getBeschreibung().equals(beschreibung))
                this.status = as;
        }
        save();
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) throws SQLException {
        this.kommentar = kommentar;
        save();
    }

    private Gruppe findMatchingGroups(int gruppeoid) throws SQLException {
        List<Gruppe> groupList = Gruppe.getAllGroups(Main.getDB());

        for(Gruppe g : groupList) {
            if(g.getGruppeOID() == gruppeoid)
                return g;
        }

        return null;
    }

    private Ansprechpartner findMatchingAnsprechpartner(int ansprechpartneroid) throws SQLException {
        List<Ansprechpartner> ansprechpartnerList = Ansprechpartner.getAllAnsprechpartner(Main.getDB());

        for(Ansprechpartner a : ansprechpartnerList) {
            if(a.getAnsprechpartnerOID() == ansprechpartneroid)
                return a;
        }

        return null;
    }

    private int autoIncAntragOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet antragSet = statement.executeQuery("SELECT * FROM Antrag");
        int rowCount = antragSet.last() ? antragSet.getRow() : 0;

        antragSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Antrag VALUES (%s, %s, %s, %s, %s, %s, %s, %s, '%s') ON DUPLICATE KEY UPDATE GruppeOID = %s, AnsprechpartnerOID = %s, Titel = %s, Skizze = %s, BeschreibungKurz = %s, BeschreibungDetails = %s, Kommentar = %s, Status = '%s'",
                antragoid,
                gruppe.getGruppeOID(),
                ansprechpartner.getAnsprechpartnerOID(),
                titel == null ? NULL : "'" + titel + "'",
                skizze == null ? NULL : "'" + skizze + "'",
                beschreibungKurz == null ? NULL : "'" + beschreibungKurz + "'",
                beschreibungDetails == null ? NULL : "'" + beschreibungDetails + "'",
                kommentar == null ? NULL : "'" + kommentar + "'",
                status.getBeschreibung(),
                gruppe.getGruppeOID(),
                ansprechpartner.getAnsprechpartnerOID(),
                titel == null ? NULL : "'" + titel + "'",
                skizze == null ? NULL : "'" + skizze + "'",
                beschreibungKurz == null ? NULL : "'" + beschreibungKurz + "'",
                beschreibungDetails == null ? NULL : "'" + beschreibungDetails + "'",
                kommentar == null ? NULL : "'" + kommentar + "'",
                status.getBeschreibung()));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Antrag> antragList) throws SQLException {
        boolean saveCheck = false;

        for(Antrag a : antragList)
            saveCheck = a.save();

        return saveCheck;
    }

    public static Antrag getAntragByGruppeOID(int gruppeoid) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet antragSet = statement.executeQuery("SELECT * FROM Antrag");

        while(antragSet.next()) {
            if(antragSet.getInt("GruppeOID") == gruppeoid) {
                return new Antrag(
                        Integer.parseInt(antragSet.getObject(1).toString()), Integer.parseInt(antragSet.getObject(2).toString()),
                        Integer.parseInt(antragSet.getObject(3).toString()), antragSet.getObject(4).toString(),
                        antragSet.getObject(5).toString(), antragSet.getObject(6).toString(),
                        antragSet.getObject(7).toString(), antragSet.getObject(8) == null ? null : antragSet.getObject(8).toString(),
                        antragSet.getObject(9).toString());
            }
        }

        return null;
    }

    public static List<Antrag> getAllAntraege(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet antragSet = statement.executeQuery("SELECT * FROM Antrag");
        List<Antrag> allAntraege = new ArrayList<>();

        while(antragSet.next()) {
            allAntraege.add(new Antrag(
                    Integer.parseInt(antragSet.getObject(1).toString()), Integer.parseInt(antragSet.getObject(2).toString()),
                    Integer.parseInt(antragSet.getObject(3).toString()), antragSet.getObject(4).toString(),
                    antragSet.getObject(5).toString(), antragSet.getObject(6).toString(),
                    antragSet.getObject(7).toString(), antragSet.getObject(8) == null ? null : antragSet.getObject(8).toString(),
                    antragSet.getObject(9).toString()));
        }
        antragSet.close();

        return allAntraege;
    }

    @Override
    public String toString() {
        if(kommentar != null && !kommentar.isEmpty())
            return String.format("AntragOID: %s\tGruppeOID: %s\tAnsprechpartnerOID: %s\nTitel: %s\nSkizze: %s\nKurze Beschreibung des Projekthintergrunds:\n%s\nDetailierte Beschreibung der Projektinhalte:\n%s\nStatus: %s\nKommentar:\n%s\n", antragoid, gruppe.getGruppeOID(), ansprechpartner.getAnsprechpartnerOID(), titel, skizze, beschreibungKurz, beschreibungDetails, status.getBeschreibung(), kommentar);
        else
            return String.format("AntragOID: %s\tGruppeOID: %s\tAnsprechpartnerOID: %s\nTitel: %s\nSkizze: %s\nKurze Beschreibung des Projekthintergrunds:\n%s\nDetailierte Beschreibung der Projektinhalte:\n%s\nStatus: %s\n", antragoid, gruppe.getGruppeOID(), ansprechpartner.getAnsprechpartnerOID(), titel, skizze, beschreibungKurz, beschreibungDetails, status.getBeschreibung());
    }
}
