package sample;

import DBAccess.Connect4DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Connect4;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class RegisterMenu implements Initializable {

    @FXML
    private VBox formularioInicioDeSesion;
    @FXML
    private VBox formularioRegistro;

    @FXML
    private TextField usuarioRegistro;
    @FXML
    private TextField contrasenaRegistro;
    @FXML
    private TextField correoRegistro;
    @FXML
    private TextField usuarioInicioSesion;
    @FXML
    private TextField contrasenaInicioSesion;

    @FXML
    private DatePicker fechaNacimientoRegistro;

    @FXML
    private TextFlow recuperarContrasena;

    @FXML
    private Label mensajeDeErrorDeRegistro;
    @FXML
    private Label mensajeDeErrorDeInicioDeSesion;


    // Creacion de Objetos de las librerias para poder acceder a los metodos
    // A parte decir que el jugador será relevante para el LogIn y Registro

    Player Jugador1;
    Player Jugador2;
    Player llamaMetodosPlayer;

    boolean registroRellenado;
    boolean ingresoRellenado;

    String usuarioNombreRegistro;
    String usuarioContrasenaRegistro;
    String usuarioCorreoRegistro;

    LocalDate nacimiento;

    Connect4 conecta4;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // La instancia para llamar al connect4 y acceder a la base de datos
        try {
            conecta4 = Connect4.getSingletonConnect4();

        } catch (Connect4DAOException e) {
            e.printStackTrace();
        }

        llamaMetodosPlayer = conecta4.getPlayer("nickName1");

        Text mensaje_de_contrasena = new Text("¿Has olvidado tu contraseña?");
        mensaje_de_contrasena.setUnderline(true);
        recuperarContrasena.getChildren().add(mensaje_de_contrasena);

        recuperarContrasena.setOnMouseClicked(ev -> {
            if (ev.getTarget() instanceof Text) {
                Text clicked = (Text) ev.getTarget();
                StackPane recuperar = new StackPane();

                TextField usuario = new TextField();
                TextField correo = new TextField();
                Button enviar = new Button();
                VBox cajaVertical = new VBox();

                cajaVertical.getChildren().add(usuario);
                cajaVertical.getChildren().add(correo);
                cajaVertical.getChildren().add(enviar);
                recuperar.getChildren().add(cajaVertical);

                cajaVertical.setPadding(new Insets(50));
                cajaVertical.setSpacing(10);
                cajaVertical.setAlignment(Pos.BOTTOM_RIGHT);
                usuario.setFocusTraversable(false);
                usuario.setPromptText("Nombre de usuario");
                correo.setFocusTraversable(false);
                correo.setPromptText("Correo electronico");
                enviar.setText("Enviar");


                Scene recuperarContrasenaEscena = new Scene(recuperar, 300, 180);

                Stage nuevaVentana = new Stage();

                nuevaVentana.setScene(recuperarContrasenaEscena);
                nuevaVentana.setMaxHeight(180);
                nuevaVentana.setMaxWidth(300);
                nuevaVentana.setMinHeight(180);
                nuevaVentana.setMinWidth(300);

                nuevaVentana.show();
            }
        });
    }

    @FXML
    protected void InicioSesion(ActionEvent event) {


        formularioRegistro.setVisible(false);
        formularioInicioDeSesion.setVisible(true);

        ingresoRellenado = (usuarioInicioSesion.getLength() != 0 && contrasenaInicioSesion.getLength() != 0);


        if (ingresoRellenado) {

            if (llamaMetodosPlayer.checkCredentials(usuarioInicioSesion.getText(), contrasenaInicioSesion.getText())) {
                try {
                    Jugador1 = conecta4.loginPlayer(usuarioInicioSesion.getText(), contrasenaInicioSesion.getText());
                    Main.setRoot("Tablero");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                mensajeDeErrorDeInicioDeSesion.setText("El usuario o contraseña son incorrectos\nIntentelo de nuevo");
            }
        }
    }

    @FXML
    public void Registro(ActionEvent event) throws IOException {


        formularioRegistro.setVisible(true);
        formularioInicioDeSesion.setVisible(false);

        // Declaracion de todos los datos almacenados en los input
        usuarioNombreRegistro = usuarioRegistro.getText();
        usuarioContrasenaRegistro = contrasenaRegistro.getText();
        usuarioCorreoRegistro = correoRegistro.getText();
        nacimiento = fechaNacimientoRegistro.getValue();

        // Simplificacion de las condiciones para saber si se está rellenando el formulario
        registroRellenado = (usuarioRegistro.getLength() != 0 && contrasenaRegistro.getLength() != 0 && correoRegistro.getLength() != 0 && fechaNacimientoRegistro.getValue() != null);

        if (formularioRegistro.isVisible()) {

            if (!Player.checkNickName(usuarioNombreRegistro)) {
                mensajeDeErrorDeRegistro.setText("Nombre de usuario no valido\nEl nombre de usuario debe tener entre 6 y 15 caracteres, contener letras mayusculas\nminusculas o _ y -");
            } else if (!Player.checkPassword(usuarioContrasenaRegistro)) {
                mensajeDeErrorDeRegistro.setText("La contraseña no es valida\nuna contraseña valida debe tener entre 8 y 20 caracteres\nal menos una letra mayuscula y minuscula\nal menos un digito\ny contener un caracter especial como ª@#$%&()-+=");
            } else if (!Player.checkEmail(usuarioCorreoRegistro)) {
                mensajeDeErrorDeRegistro.setText("Correo no valido");
            } else if (!(LocalDate.now().minusYears(nacimiento.getYear()).getYear() >= 18)) {
                mensajeDeErrorDeRegistro.setText("Debe ser mayor a 18 años");
            } else if (!llamaMetodosPlayer.checkCredentials(usuarioNombreRegistro, usuarioContrasenaRegistro)) {
                try {
                    Jugador1 = conecta4.registerPlayer(usuarioNombreRegistro, usuarioCorreoRegistro, usuarioContrasenaRegistro, nacimiento, 0);
                } catch (Connect4DAOException e) {
                    e.printStackTrace();
                }

                Main.setRoot("Tablero");
            }
        }
    }


    public void borrarJugador1() {
        Jugador1 = null;
    }

    public void borrarJugador2() {
        Jugador2 = null;
    }

    public Player getJugador1() {
        return Jugador1;
    }

    public Player getJugador2() {
        return Jugador2;
    }

    public void setJugador2(String nombre, String contrasena) {
        Jugador2 = conecta4.loginPlayer(nombre, contrasena);
    }


    public void resetFields() {
        usuarioInicioSesion.clear();
        usuarioRegistro.clear();
        contrasenaInicioSesion.clear();
        contrasenaRegistro.clear();
        correoRegistro.clear();
        fechaNacimientoRegistro.getEditor().clear();

    }
}
