package sample;

import DBAccess.Connect4DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Connect4;
import model.Player;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAmount;


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

    // Creacion de Objetos de las librerias para poder acceder a los metodos
    // A parte decir que el Jugador1 será relevante para el LogIn y Registro
    Player Jugador1 = null;
    Connect4 conecta4;

    // La instancia para llamar al connect4 y acceder a la base de datos
    {
        try {
            conecta4 = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException e) {
            e.printStackTrace();
        }
    }
    /**
    protected boolean LimpiarDatos(String nombre, String contrasena, String correo, LocalDate nacimiento) {
        boolean correcto = false;
        Player jugador = null;

        if (!conecta4.exitsNickName(nombre)) { correcto = true; }
        else { System.out.println("El nombre no exite"); }

        if (jugador.checkPassword(contrasena)) { correcto = true; }
        else { System.out.println("La contraseña no es valida"); }

        if (jugador.checkEmail(correo)) { correcto = true; }
        else { System.out.println("El correo no es valido"); }

        if (LocalDate.now().minusYears(nacimiento.getYear()));
        return correcto;
    }
        **/

    // Metodo unico encargado de la ejecucion de los botones LogIn y Register
    @FXML  protected void CambioRegistroInicioSesion(ActionEvent event) throws IOException {
        // Declaracion de todos los datos almacenados en los input
         String usuarioNombreInicioSesion = usuarioInicioSesion.getText();
         String usuarioContrasenaInicioSesion = contrasenaInicioSesion.getText();
         String usuarioNombreRegistro = usuarioRegistro.getText();
         String usuarioContrasenaRegistro = contrasenaRegistro.getText();
         String usuarioCorreoRegistro = correoRegistro.getText();
         LocalDate nacimiento = fechaNacimientoRegistro.getValue();

         // Simplificacion de las condiciones para saber si se está rellenando el formulario
        boolean registroRellenado = ( usuarioRegistro.getLength() != 0 && contrasenaRegistro.getLength() != 0 && correoRegistro.getLength() != 0 && fechaNacimientoRegistro.getValue() != null );
        boolean ingresoRellenado  = ( usuarioInicioSesion.getLength() != 0 && contrasenaInicioSesion.getLength() != 0 );


        if (formularioRegistro.isVisible()) {                                       // Si   Visible el formulario de Registro
            if (registroRellenado && registrarse.isArmed()) {                       //          Formulario de Registro cumplimentado + Click
               /** if (LimpiarDatos())
                try {
                    Jugador1 = conecta4.registerPlayer(usuarioNombreRegistro, usuarioCorreoRegistro, usuarioContrasenaRegistro, nacimiento, 0);
                } catch (Connect4DAOException e) {
                    e.printStackTrace();
                }
                **/
                Main.setRoot("Tablero");

            } else if (iniciarSesion.isArmed()) {                                   // Sino     Cambio de VBox de Registro a Inicio sesion
                formularioRegistro.setVisible(false);
                formularioInicioDeSesion.setVisible(true);
            }

        } else if (formularioInicioDeSesion.isVisible()) {                          // Si   Visible el formulario de Inicio sesion
            if (ingresoRellenado && iniciarSesion.isArmed()) {                      //          Formulario de Inicio sesion cumplimentado + Click
                /**
                if (Jugador1.checkCredentials(usuarioNombreInicioSesion, usuarioContrasenaInicioSesion)) {
                    //Jugador1 = conecta4.loginPlayer(usuarioNombreInicioSesion, usuarioContrasenaInicioSesion);
                    Main.setRoot("Tablero");

                } else { System.out.println("No existe un jugador con ese" +
                        " nombre y esa contraseña por favor intentalo de nuevo"); }
                 **/
                // Esta instruccion hay que borrarla al comprobar que funciona la autentificacion de usuario
                Main.setRoot("Tablero");

            } else if (registrarse.isArmed()) {                                     // Sino    Cambio de VBox de Inicio sesion a Registro
                formularioRegistro.setVisible(true);
                formularioInicioDeSesion.setVisible(false);
            }
        }
    }
}
