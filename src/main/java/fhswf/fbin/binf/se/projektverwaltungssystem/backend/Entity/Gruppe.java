package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.sql.JDBCType.NULL;

public class Gruppe {
    private Integer gruppeoid;

    private Integer projektoid;

    private Integer moduloid;

    private String titel;

    private Integer pin;

    /**
     * Creating new Group instance
     * @param titel
     * @param moduloid
     * @throws SQLException
     */
    public Gruppe(String titel, int moduloid) throws SQLException {
        this.gruppeoid = autoIncGruppeOID();
        this.moduloid = moduloid;
        this.titel = titel;
        this.pin = generatePin();
        save();
    }

    /**
     * Loading Group instance from Database
     * @param gruppeoid
     * @param projektoid
     * @param titel
     * @param moduloid
     * @param pin
     * @throws SQLException
     */
    public Gruppe(int gruppeoid, int projektoid, int moduloid, String titel,  int pin) throws SQLException {
        this.gruppeoid = gruppeoid;
        this.projektoid = projektoid;
        this.moduloid = moduloid;
        this.titel = titel;
        this.pin = pin;
    }

    public int getGruppeOID() {
        return gruppeoid;
    }

    public void setGruppeOID(int gruppeoid) {
        this.gruppeoid = gruppeoid;
    }

    public Integer getProjektoid() {
        return projektoid;
    }

    public void setProjektoid(Integer projektoid) {
        this.projektoid = projektoid;
    }

    public Projekt getProjekt() throws SQLException {
        return findMatchingProjekt(projektoid);
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Integer getModuloid() {
        return moduloid;
    }

    public void setModuloid(Integer moduloid) {
        this.moduloid = moduloid;
    }

    public Modul getModul() throws SQLException {
        return findMatchingModul(moduloid);
    }

    public int getPin() {
        return pin;
    }

    public int generatePin() {
        Random rnd = new Random();
        return 1000 + rnd.nextInt(9000);
    }

    public boolean containsMatrikelnummer(String matrikelnummer) throws SQLException {
        boolean check = false;

        for(Student s : getMitglieder()) {
            if(s.getMatrikelnummer().equals(matrikelnummer))
                check = true;
        }

        return check;
    }

    private Projekt findMatchingProjekt(int projektoid) throws SQLException {
        List<Projekt> projectList = Projekt.getAllProjects(Main.getDB());

        for(Projekt p : projectList) {
            if(p.getProjektOID() == projektoid)
                return p;
        }

        return null;
    }

    private Modul findMatchingModul(int moduloid) throws SQLException {
        List<Modul> modulList = Modul.getAllModule(Main.getDB());

        for(Modul m : modulList) {
            if(m.getModulOID() == moduloid)
                return m;
        }

        return null;
    }

    public List<Student> getMitglieder() throws SQLException {
        return Gruppenbildung.getMitgliederByGruppeOID(gruppeoid);
    }

    private int autoIncGruppeOID() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet gruppeSet = statement.executeQuery("SELECT * FROM Gruppe");
        int rowCount = gruppeSet.last() ? gruppeSet.getRow() : 0;

        gruppeSet.close();
        return rowCount + 1;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Gruppe VALUES (%s, %s, %s, '%s', %s) ON DUPLICATE KEY UPDATE ProjektOID = %s, ModulOID = %s, Titel = '%s', Pin = %s",
                gruppeoid,
                projektoid == null ? NULL : projektoid,
                moduloid == null ? NULL : moduloid,
                titel,
                pin,
                projektoid == null ? NULL : projektoid,
                moduloid == null ? NULL : moduloid,
                titel,
                pin));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Gruppe> groupList) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        boolean saveCheck = false;

        for(Gruppe g : groupList)
            saveCheck = g.save();

        return saveCheck;
    }

    public static List<Gruppe> getAllGroupsForStudent(Student student) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet bildungSet = statement.executeQuery("SELECT * FROM Gruppenbildung");
        List<Gruppe> allGroups = getAllGroups(Main.getDB());
        List<Gruppe> allGroupsForStudent = new ArrayList<>();

        while(bildungSet.next()) {
            for(Gruppe g : allGroups) {
                if(bildungSet.getString("Matrikelnummer").equals(student.getMatrikelnummer()) && bildungSet.getInt("GruppeOID") == g.getGruppeOID())
                    allGroupsForStudent.add(g);
            }
        }

        return allGroupsForStudent;
    }

    public static List<Gruppe> getAllGroups(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet groupSet = statement.executeQuery("SELECT * FROM Gruppe");
        List<Gruppe> allGroups = new ArrayList<>();

        while(groupSet.next()) {
            allGroups.add(new Gruppe(groupSet.getInt("GruppeOID"), groupSet.getInt("ProjektOID"), groupSet.getInt("ModulOID"), groupSet.getString("Titel"), groupSet.getInt("Pin")));
        }
        groupSet.close();

        return allGroups;
    }

    public List<Antrag> getAllAntraegeByGroup() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet antragSet = statement.executeQuery("SELECT * FROM Antrag WHERE GruppeOID = " + gruppeoid);
        List<Antrag> antraege = new ArrayList<>();

        while(antragSet.next())
            antraege.add(new Antrag(
                    Integer.parseInt(antragSet.getObject(1).toString()), Integer.parseInt(antragSet.getObject(2).toString()),
                    Integer.parseInt(antragSet.getObject(3).toString()), antragSet.getObject(4).toString(),
                    antragSet.getObject(5).toString(), antragSet.getObject(6).toString(),
                    antragSet.getObject(7).toString(), antragSet.getObject(8).toString(),
                    antragSet.getObject(9).toString()));

        return antraege;
    }

    @Override
    public String toString() {
        if(projektoid != null)
            return String.format("GruppeOID: %s\tProjektOID: %s\tModulOID: %s\t\t\tTitel: %s\tPIN: %s", gruppeoid, projektoid, moduloid, titel, pin);
        else
            return String.format("GruppeOID %s\tProjektOID: Kein Projekt vorhanden\tModulOID: %s\tTitel: %s\tModul: %s\tPIN: %s", gruppeoid, moduloid, titel, pin);
    }
}
