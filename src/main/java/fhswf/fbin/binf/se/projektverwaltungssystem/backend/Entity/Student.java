package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student {

    private String matrikelnummer;

    private String name;

    private String vorname;

    public Student(String vorname, String name) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.matrikelnummer = generateMatrikelnummer();
        save();
    }

    public Student(String matrikelnummer, String vorname, String name) throws SQLException {
        this.name = name;
        this.vorname = vorname;
        this.matrikelnummer = matrikelnummer;
        save();
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
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

    private String generateMatrikelnummer() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet studentSet = statement.executeQuery("SELECT Matrikelnummer FROM Student");
        Random rnd = new Random();
        String matNr = String.valueOf(10000000 + rnd.nextInt(90000000));
        boolean isMatNrTaken = false;

        while(studentSet.next()) {
            if(studentSet.getObject(1) != null) {
                if(studentSet.getObject(1).toString().equals(matNr))
                    isMatNrTaken = true;
            }
        }

        if(isMatNrTaken)
            matNr = generateMatrikelnummer();

        return matNr;
    }

    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Student VALUES ('%s', '%s', '%s') ON DUPLICATE KEY UPDATE Vorname = '%s', Name = '%s'",
                matrikelnummer,
                vorname,
                name,
                vorname,
                name));

        return affectedRows > 0;
    }

    public static boolean saveAll(List<Student> studentList) throws SQLException {
        boolean saveCheck = false;

        for(Student s : studentList)
            saveCheck = s.save();

        return saveCheck;
    }

    public static Student getStudentByMatNr(String matrikelnummer) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet studentSet = statement.executeQuery("SELECT * FROM Student WHERE Matrikelnummer = " + matrikelnummer);

        studentSet.next();
        return new Student(matrikelnummer, studentSet.getObject(2).toString(), studentSet.getObject(3).toString());
    }

    public static List<Student> getAllStudents(DatabaseConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        ResultSet studentSet = statement.executeQuery("SELECT * FROM Student");
        List<Student> allStudents = new ArrayList<>();

        while(studentSet.next()) {
            allStudents.add(new Student(studentSet.getObject(1).toString(), studentSet.getObject(2).toString(), studentSet.getObject(3).toString()));
        }
        studentSet.close();

        return allStudents;
    }

    @Override
    public String toString() {
        if(name.length() <= 5)
            return String.format("Matrikelnummer: %s\tName: %s\t\t\tVorname: %s", matrikelnummer, name, vorname);
        else
            return String.format("Matrikelnummer: %s\tName: %s\t\tVorname: %s", matrikelnummer, name, vorname);
    }
}
