package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class RegisterMenu {
    @FXML private VBox formularioDeInicioDeSesion;
    @FXML private VBox formularioDeRegistro;
    @FXML private TextField UsuarioRegistro;
    @FXML private TextField ContraseñaRegistro;
    @FXML private TextField correoRegistro;
    @FXML private DatePicker FechaNacimientoRegistro;
    @FXML private TextField nombreUsuarioInicioSesion;
    @FXML private TextField contraseñaInicioSesion;
    @FXML private Button iniciarSesion;
    @FXML private Button Registrarse;

    boolean registroRellenado = ( UsuarioRegistro.getLength() == 0 || ContraseñaRegistro.getLength() == 0 || correoRegistro.getLength() == 0 || FechaNacimientoRegistro.getValue() == null );
    boolean ingresoRellenado  = ( nombreUsuarioInicioSesion.getLength() != 0 && contraseñaInicioSesion.getLength() != 0 );

    @FXML  protected void cambioRegistroIngreso(ActionEvent event) {

        if (formularioDeRegistro.isVisible()) {
            if (registroRellenado && Registrarse.isArmed()) {
                System.out.println("Mi abuela en patinete");
            } else if (!registroRellenado && Registrarse.isArmed()) {
                formularioDeRegistro.setVisible(false);
                formularioDeInicioDeSesion.setVisible(true);
            }
        }

        if (formularioDeInicioDeSesion.isVisible()) {
            if (ingresoRellenado && iniciarSesion.isArmed()) {
                System.out.println("mi patinete en mi abuela");
            } else if (!ingresoRellenado && iniciarSesion.isArmed()) {
                formularioDeInicioDeSesion.setVisible(false);
                formularioDeRegistro.setVisible(true);
            }
        }

    }

}
