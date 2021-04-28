package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class RegisterMenu {
    @FXML  private VBox formularioDeInicioDeSesion;
    @FXML  private VBox formularioDeRegistro;
    @FXML  private TextField UsuarioRegistro;
    @FXML  private TextField ContraseñaRegistro;
    @FXML  private TextField correoRegistro;
    @FXML  private DatePicker FechaNacimientoRegistro;
    @FXML  private TextField nombreUsuarioInicioSesion;
    @FXML  private TextField contraseñaInicioSesion;
    @FXML  private Button iniciarSesion;
    @FXML  private Button Registrarse;

    @FXML  protected void cambioRegistroIngreso(ActionEvent event) {
        if (UsuarioRegistro.getLength() == 0 || ContraseñaRegistro.getLength() == 0 || correoRegistro.getLength() == 0 || FechaNacimientoRegistro.getValue() == null){
            if (formularioDeRegistro.isVisible() && iniciarSesion.isArmed()){
                formularioDeInicioDeSesion.setVisible(true);
                formularioDeRegistro.setVisible(false);
            } else if (Registrarse.isArmed()){
                formularioDeInicioDeSesion.setVisible(false);
                formularioDeRegistro.setVisible(true);
            }
        } else if (nombreUsuarioInicioSesion.getLength() != 0 && contraseñaInicioSesion.getLength() != 0) {
            if (iniciarSesion.isArmed()){
                System.out.println("Mi abuela en patinete");
            } else if (Registrarse.isArmed()){
                System.out.println("Mi patinete en mi abuela");
            }
        }

    }

}
