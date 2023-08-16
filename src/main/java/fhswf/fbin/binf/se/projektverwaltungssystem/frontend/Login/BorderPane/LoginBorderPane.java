package fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.BorderPane;

import fhswf.fbin.binf.se.projektverwaltungssystem.frontend.Login.GridPane.LoginGridPane;
import javafx.scene.layout.BorderPane;

public class LoginBorderPane extends BorderPane {
    public LoginBorderPane() {
        setCenter(new LoginGridPane());
    }
}
