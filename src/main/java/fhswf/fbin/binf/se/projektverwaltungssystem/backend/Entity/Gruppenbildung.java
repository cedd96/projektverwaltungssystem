package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Gruppenbildung {
    private List<Student> studenten;

    private Gruppe gruppe;

    public Gruppenbildung(Student ersteller, Gruppe gruppe) throws SQLException {
        this.studenten = new ArrayList<>();
        this.studenten.add(ersteller);
        this.gruppe = gruppe;
        save();
    }

    public Gruppenbildung(String erstellerMatNr, Gruppe gruppe) throws SQLException {
        this.studenten = new ArrayList<>();
        this.studenten.add(findMatchingStudent(erstellerMatNr));
        this.gruppe = gruppe;
        save();
    }

    public Gruppenbildung(String matrikelnummer, int gruppeoid) throws SQLException {
        if(this.studenten == null)
            this.studenten = new ArrayList<>();
        this.studenten.add(findMatchingStudent(matrikelnummer));
        this.gruppe = findMatchingGroups(gruppeoid);
        save();
    }

    public List<Student> getStudenten() {
        return studenten;
    }

    public void addStudent(Student student) throws SQLException {
        this.studenten.add(student);
        save();
    }

    public void addStudent(String matrikelnummer) throws SQLException {
        this.studenten.add(findMatchingStudent(matrikelnummer));
        save();
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

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = 0;

        for(Student s : studenten)
            affectedRows += statement.executeUpdate(String.format("INSERT INTO Gruppenbildung VALUES ('%s', %s) ON DUPLICATE KEY UPDATE Matrikelnummer = '%s', GruppeOID = %s",
                    s.getMatrikelnummer(),
                    gruppe.getGruppeOID(),
                    s.getMatrikelnummer(),
                    gruppe.getGruppeOID()));

        return affectedRows > 0;
    }

    private static Student findMatchingStudent(String matrikelnummer) throws SQLException {
        List<Student> studentList = Student.getAllStudents(Main.getDB());

        for(Student s : studentList) {
            if(s.getMatrikelnummer().equals(matrikelnummer))
                return s;
        }

        return null;
    }

    private static Gruppe findMatchingGroups(int gruppeoid) throws SQLException {
        List<Gruppe> groupList = Gruppe.getAllGroups(Main.getDB());

        for(Gruppe g : groupList) {
            if(g.getGruppeOID() == gruppeoid)
                return g;
        }

        return null;
    }

    public static List<Student> getMitgliederByGruppeOID(int gruppeoid) throws SQLException {
        List<Student> studenten = new ArrayList<>();
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet grpbldSet = statement.executeQuery("SELECT * FROM Gruppenbildung");

        while(grpbldSet.next()) {
            if(Integer.parseInt(grpbldSet.getObject(2).toString()) == gruppeoid)
                studenten.add(findMatchingStudent(grpbldSet.getObject(1).toString()));
        }

        return studenten;
    }

    public static List<Gruppenbildung> getAllGruppenbildungen(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet grpbldSet = statement.executeQuery("SELECT * FROM Gruppenbildung");
        ResultSet tmpSet = null;
        List<Gruppenbildung> allGruppenbildungen = new ArrayList<>();
        int listIndex = 0;

        while(grpbldSet.next()) {
            allGruppenbildungen.add(new Gruppenbildung(grpbldSet.getObject(1).toString(), Integer.parseInt(grpbldSet.getObject(2).toString())));
            tmpSet = grpbldSet;
            while(grpbldSet.next()) {
                if(tmpSet.getObject(2).toString().equals(grpbldSet.getObject(2).toString())) {
                    allGruppenbildungen.get(listIndex).addStudent(grpbldSet.getObject(1).toString());
                }
            }
            listIndex++;
            tmpSet.close();
        }
        grpbldSet.close();

        return allGruppenbildungen;
    }

    public static boolean saveAll(List<Gruppenbildung> grpbldList) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        boolean saveCheck = false;

        for(Gruppenbildung gb: grpbldList)
            saveCheck = gb.save();

        return saveCheck;
    }

    @Override
    public String toString() {
        String result = null;

        for(Student s : studenten)
            result += String.format("GruppeOID: %s\tMatrikelnummer: %s\n", gruppe.getGruppeOID(), s.getMatrikelnummer());

        return result;
    }
}
