package fhswf.fbin.binf.se.projektverwaltungssystem.backend.JDBC;

import fhswf.fbin.binf.se.projektverwaltungssystem.Main;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Dozent;
import fhswf.fbin.binf.se.projektverwaltungssystem.backend.Entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.JDBCType.NULL;

/**
 * LoginHandler stellt Methoden für die Login-Verifizierung und Registrierungen bereit.
 *
 * @author Cedric Schauerte
 * @version 1.0
 */
public class LoginHandler {
    private Dozent dozent;

    private Student student;

    private String nutzerkennung;

    private String passwort;

    /**
     * Variable für die Nutzerverwaltung.
     */
    private static Object currentUser = null;

    /**
     * Variable für die Nutzerverwaltung.
     */
    private static String currentUserName = null;

    /**
     * Der Konstruktor wird im Falle einer Registrierung aufgerufen, dabei wird ein neuer Student oder
     * Dozent in der Datenbank angelegt und die Login Informationen werden gespeichert.
     * @param membership Ein String der über den Hochschulstatus des Nutzers entscheidet (Student/in o. Dozent/in).
     * @param vorname Der Vorname der Person.
     * @param name Der Nachname der Person.
     * @param nutzerkennung Die Nutzerkennung der Person.
     * @param passwort
     * @throws SQLException
     */
    public LoginHandler(String membership, String vorname, String name, String nutzerkennung, String passwort) throws SQLException {
        if(membership.equals("Student/in")) {
            this.student = new Student(vorname, name);
            this.nutzerkennung = nutzerkennung;
            this.passwort = passwort;
        }
        else if(membership.equals("Dozent/in")) {
            this.dozent = new Dozent(vorname, name);
            this.nutzerkennung = nutzerkennung;
            this.passwort = passwort;
        }
        save();
    }

    /**
     * Checks if the entered Username is unique
     * @param nutzerkennung
     * @return returns true if the username isnt already registered in the database, returns false otherwise
     * @throws SQLException
     */
    public static boolean isUsernameUnique(String nutzerkennung) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet loginSet = statement.executeQuery("SELECT Nutzerkennung FROM Login");

        while(loginSet.next()) {
            if(loginSet.getObject(1).toString().equals(nutzerkennung))
                return false;
        }

        return true;
    }

    /**
     * Checks if the entered username is registered
     * @param nutzerkennung
     * @return
     * @throws SQLException
     */
    public static boolean usernameExists(String nutzerkennung) throws SQLException {
        return !isUsernameUnique(nutzerkennung);
    }

    public static boolean isPasswordValid(String nutzerkennung, String passwort) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet loginSet = statement.executeQuery("SELECT Nutzerkennung, Passwort FROM Login");

        while(loginSet.next()) {
            if(loginSet.getObject(1).toString().equals(nutzerkennung) && loginSet.getObject(2).toString().equals(passwort))
                return true;
        }

        return false;
    }

    /**
     * Returns either Student or Dozent matched with the input Nutzerkennung or returns null if said object doesnt exist.
     * @param nutzerkennung
     * @return
     * @throws SQLException
     */
    public static Object loadUserFromDB(String nutzerkennung) throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet loginSet = statement.executeQuery("SELECT * FROM Login");

        Object o = null;

        while(loginSet.next()) {
            if(loginSet.getObject(1).toString().equals(nutzerkennung) && loginSet.getObject(2) != null) {
                o = Dozent.getDozentByOID(Integer.valueOf(loginSet.getObject(2).toString()));
                break;
            }
            else if(loginSet.getObject(1).toString().equals(nutzerkennung) && loginSet.getObject(2) == null) {
                o = Student.getStudentByMatNr(loginSet.getObject(3).toString());
                break;
            }
        }
        loginSet.close();

        return o;
    }

    /**
     * Die Methode speichert die Daten dieses LoginHandlers in der Datenbank ab.
     * @return Anzahl der gespeicherten Zeilen in der Datenbank
     * @throws SQLException
     */
    public boolean save() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        int affectedRows = statement.executeUpdate(String.format("INSERT INTO Login VALUES ('%s', %s, %s, '%s') ON DUPLICATE KEY UPDATE DozentOID = %s, Matrikelnummer = %s, Passwort = '%s'",
                nutzerkennung,
                dozent == null ? NULL : dozent.getDozentOID(),
                student == null ? NULL : "'" + student.getMatrikelnummer() + "'",
                passwort,
                dozent == null ? NULL : dozent.getDozentOID(),
                student == null ? NULL : "'" + student.getMatrikelnummer() + "'",
                passwort));

        return affectedRows > 0;
    }

    /**
     * Returns the username of the current User
     * @return Username of the current User
     * @throws SQLException
     */
    public static String getCurrentUserIdentifier() throws SQLException {
        Statement statement = Main.getDB().getConnection().createStatement();
        ResultSet loginSet = statement.executeQuery("SELECT Nutzerkennung, Matrikelnummer, DozentOID FROM Login");

        while(loginSet.next()) {
            if(currentUser instanceof Student) {
                if(((Student) currentUser).getMatrikelnummer().equals(loginSet.getString("Matrikelnummer")))
                    return loginSet.getString("Nutzerkennung");
            }
            else if(currentUser instanceof Dozent) {
                if(((Dozent) currentUser).getDozentOID().toString().equals(loginSet.getString("DozentOID")))
                    return loginSet.getString("Nutzerkennung");
            }
        }

        return null;
    }

    public static void setCurrentUser(Object user) {
        currentUser = user;
    }

    public static Object getCurrentUser() {
        return currentUser;
    }
}
