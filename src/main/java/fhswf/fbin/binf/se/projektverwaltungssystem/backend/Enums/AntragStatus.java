package fhswf.fbin.binf.se.projektverwaltungssystem.backend.Enums;

public enum AntragStatus {
    VERSENDET("Antrag versendet / warten auf Evaluation"),
    ZUGELASSEN("Antrag angenommen"),
    ABGELEHNT("Antrag abgelehnt"),
    BEARBEITEN("Der Antrag muss überarbeitet werden"),
    FEHLT("Es wurde noch kein Antrag versendet"),
    ABGESCHLOSSEN("Projekt abgeschlossen"),
    ;

    private String beschreibung;

    AntragStatus(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public String toString() {
        return beschreibung;
    }
}
