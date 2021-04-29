package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class RegisterMenu {
    @FXML  private VBox         formularioInicioDeSesion;
    @FXML  private VBox         formularioRegistro;
    @FXML  private TextField    usuarioRegistro;
    @FXML  private TextField    contrasenaRegistro;
    @FXML  private TextField    correoRegistro;
    @FXML  private DatePicker   fechaNacimientoRegistro;
    @FXML  private TextField    usuarioInicioSesion;
    @FXML  private TextField    contrasenaInicioSesion;
    @FXML  private Button       iniciarSesion;
    @FXML  private Button       registrarse;



    @FXML  protected void CambioRegistroInicioSesion(ActionEvent event) {

        boolean registroRellenado = ( usuarioRegistro.getLength() != 0 && contrasenaRegistro.getLength() != 0 && correoRegistro.getLength() != 0 && fechaNacimientoRegistro.getValue() != null );
        boolean ingresoRellenado  = ( usuarioInicioSesion.getLength() != 0 && contrasenaInicioSesion.getLength() != 0 );


        if (formularioRegistro.isVisible()) {                                       // Visible el formulario de Registro
            if (registroRellenado && registrarse.isArmed()) {                       // Formulario de Registro cumplimentado + Click
                System.out.println("Mi abuela en patinete");

            } else if (!registroRellenado && registrarse.isArmed()) {               // Cambio de VBox de Registro a Inicio sesion
                formularioRegistro.setVisible(false);
                formularioInicioDeSesion.setVisible(true);
            }
        } else if (formularioInicioDeSesion.isVisible()) {                          // Visible el formulario de Inicio sesion
            if (ingresoRellenado && iniciarSesion.isArmed()) {                      // Formulario de Inicio sesion cumplimentado + Click
                System.out.println("mi patinete en mi abuela");

            } else if (!ingresoRellenado && iniciarSesion.isArmed()) {              // Cambio de VBox de Inicio sesion a Registro
                formularioRegistro.setVisible(true);
                formularioInicioDeSesion.setVisible(false);

            }
        }
    }
}
