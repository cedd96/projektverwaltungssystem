package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.NULL;

public class Projekt {
    private Integer projektoid;

    private Integer gruppeoid;

    private LocalDateTime termin1;

    private LocalDateTime termin2;

    private boolean termin_angefragt;

    private boolean terminanfrage_beantwortet;

    public Projekt(int gruppeoid) throws SQLException {
        this.projektoid = autoIncProjektOID();
        this.gruppeoid = gruppeoid;
        this.termin1 = null;
        this.termin2 = null;
        this.termin_angefragt = false;
        this.terminanfrage_beantwortet = false;
        save();
    }

    public Projekt(int projektoid, int gruppeoid, LocalDateTime termin1, LocalDateTime termin2, boolean termin_angefragt, boolean terminanfrage_beantwortet) throws SQLException {
        this.projektoid = projektoid;
        this.gruppeoid = gruppeoid;
        this.termin1 = termin1;
        this.termin2 = termin2;
        this.termin_angefragt = termin_angefragt;
        this.terminanfrage_beantwortet = terminanfrage_beantwortet;
        //save();
    }

    public Integer getProjektOID() {
        return projektoid;
    }

    public void setProjektOID(Integer projektoid) throws SQLException {
        this.projektoid = projektoid;
        save();
    }

    public Integer getGruppeoid() {
        return gruppeoid;
    }

    public void setGruppeoid(Integer gruppeoid) throws SQLException {
        this.gruppeoid = gruppeoid;
        save();
    }

    public Gruppe getGruppe() throws SQLException {
        return findMatchingGroup(gruppeoid);
    }

    public LocalDateTime getTermin1() {
        return termin1;
    }

    public void setTermin1(LocalDateTime termin1) throws SQLException {
        this.termin1 = termin1;
        save();
    }

    public LocalDateTime getTermin2() {
        return termin2;
    }

    public void setTermin2(LocalDateTime termin2) throws SQLException {
        this.termin2 = termin2;
        save();
    }

    public static LocalDateTime terminToLocalDateTime(String localDateTimeString) {
        if(localDateTimeString != null && !localDateTimeString.isEmpty())
            return LocalDateTime.parse(localDateTimeString.replaceAll("\\s", "T"));
        else
            return null;
    }

    public static String terminToString(LocalDateTime termin) {
        LocalDate date = termin.toLocalDate();
        LocalTime time = termin.toLocalTime();

        return String.format("Datum: %2s.%2s.%4s\tUhrzeit: %2s:%2s", date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth(), date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue(), date.getYear(), time.getHour() < 10 ? "0" + time.getHour() : time.getHour(), time.getMinute() < 10 ? "0" + time.getMinute(): time.getMinute());
    }

    public boolean hasTerminAngefragt() {
        return termin_angefragt;
    }

    public void setTerminAngefragt(boolean state) throws SQLException {
        termin_angefragt = state;
        save();
    }

    public boolean hasTerminanfrageBeantwortet() {
        return terminanfrage_beantwortet;
    }

    public void setTerminanfrageBeantwortet(boolean state) throws SQLException {
        terminanfrage_beantwortet = state;
        termin_angefragt = false;
        save();
    }

    private Gruppe findMatchingGroup(int gruppeoid) throws SQLException {
        List<Gruppe> groupList = Gruppe.getAllGroups(Main.getDB());

        for(Gruppe g : groupList) {
            if(g.getGruppeOID() == gruppeoid)
                return g;
        }

        return null;
    }

    private int autoIncProjektOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet projektSet = statement.executeQuery("SELECT * FROM Projekt");
        int rowCount = projektSet.last() ? projektSet.getRow() : 0;

        projektSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Projekt VALUES (%s, %s, %s, %s, %s, %s) ON DUPLICATE KEY UPDATE GruppeOID = %s, Termin1 = %s, Termin2 = %s, Termin_angefragt = %s, Terminanfrage_beantwortet = %s;",
                projektoid,
                gruppeoid,
                termin1 == null ? NULL : "'" + Timestamp.valueOf(termin1) + "'",
                termin2 == null ? NULL : "'" + Timestamp.valueOf(termin2) + "'",
                termin_angefragt,
                terminanfrage_beantwortet,
                gruppeoid,
                termin1 == null ? NULL : "'" + Timestamp.valueOf(termin1) + "'",
                termin2 == null ? NULL : "'" + Timestamp.valueOf(termin2) + "'",
                termin_angefragt,
                terminanfrage_beantwortet));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Projekt> projektList) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        boolean saveCheck = false;

        for(Projekt p : projektList)
            saveCheck = p.save();

        return saveCheck;
    }

    public static List<Projekt> getAllProjects(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet projectSet = statement.executeQuery("SELECT * FROM Projekt");
        List<Projekt> allProjects = new ArrayList<>();

        while(projectSet.next()) {
            allProjects.add(new Projekt(projectSet.getInt("ProjektOID"), projectSet.getInt("GruppeOID"), terminToLocalDateTime(projectSet.getString("Termin1")), terminToLocalDateTime(projectSet.getString("Termin2")), projectSet.getBoolean("Termin_angefragt"), projectSet.getBoolean("Terminanfrage_beantwortet")));
        }
        projectSet.close();

        return allProjects;
    }

    @Override
    public String toString() {
        return String.format("ProjektOID: %s\tGruppeOID: %s\tTermin 1: %s\tTermin 2: %s\tTermin angefragt: %s\tTerminanfrage beantwortet: %s", projektoid, gruppeoid, termin1, termin2, termin_angefragt, terminanfrage_beantwortet);
    }
}
