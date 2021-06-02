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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Connect4;
import model.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


    @FXML
    private Button modoOscuro2;

    @FXML
    private ImageView ImgVwavatar;

    // Creacion de Objetos de las librerias para poder acceder a los metodos
    // A parte decir que el jugador será relevante para el LogIn y Registro

    private static Player Jugador1;
    private static Player Jugador2;
    static Player llamaMetodosPlayer;

    private boolean registroRellenado;
    private boolean ingresoRellenado;

    private String usuarioNombreRegistro;
    private String usuarioContrasenaRegistro;
    private String usuarioCorreoRegistro;

    private LocalDate nacimiento;


    public static Connect4 conecta4;

    public static TableroController tablero;
    private static boolean modoOscuroRule2 = false;


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

                enviar.setOnMouseClicked(event -> {
                    Label mensaje = new Label();
                    mensaje.setTextFill(Color.RED);
                    mensaje.setText("Correo enviado");
                    cajaVertical.getChildren().add(mensaje);
                });

            }
        });
    }

    @FXML
    protected void InicioSesion() {


        formularioRegistro.setVisible(false);
        formularioInicioDeSesion.setVisible(true);
        resetearCamposRegistro();

        ingresoRellenado = (usuarioInicioSesion.getLength() != 0 && contrasenaInicioSesion.getLength() != 0);


        if (ingresoRellenado && formularioInicioDeSesion.isVisible()) {
            Jugador1 = conecta4.loginPlayer(usuarioInicioSesion.getText(), contrasenaInicioSesion.getText());
            if (Jugador1.checkCredentials(usuarioInicioSesion.getText(), contrasenaInicioSesion.getText())) {
                try {
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
    public void Registro() throws IOException {

        formularioRegistro.setVisible(true);
        formularioInicioDeSesion.setVisible(false);
        resetearCamposInicioSesion();

        // Declaracion de todos los datos almacenados en los inputresetearCamposInicioSesion();
        usuarioNombreRegistro = usuarioRegistro.getText();
        usuarioContrasenaRegistro = contrasenaRegistro.getText();
        usuarioCorreoRegistro = correoRegistro.getText();
        nacimiento = fechaNacimientoRegistro.getValue();

        // Simplificacion de las condiciones para saber si se está rellenando el formulario
        registroRellenado = (usuarioRegistro.getLength() != 0 && contrasenaRegistro.getLength() != 0 && correoRegistro.getLength() != 0 && fechaNacimientoRegistro.getValue() != null);

        if (registroRellenado && formularioRegistro.isVisible()) {

            if (!Player.checkNickName(usuarioNombreRegistro)) {
                mensajeDeErrorDeRegistro.setText("Nombre de usuario no valido\nEl nombre de usuario debe tener entre 6 y 15 caracteres, contener letras mayusculas,\nminusculas sin espacios\n en su lugar usar '_' o '-'");
            } else if (!Player.checkPassword(usuarioContrasenaRegistro)) {
                mensajeDeErrorDeRegistro.setText("La contraseña no es valida\nuna contraseña valida debe tener entre 8 y 20 car�cteres\nal menos una letra mayuscula y minuscula\nal menos un digito\ny contener un caracter especial como !@#$%&()-+=");
            } else if (!Player.checkEmail(usuarioCorreoRegistro)) {
                mensajeDeErrorDeRegistro.setText("Correo no valido");
            } else if (!(LocalDate.now().minusYears(nacimiento.getYear()).getYear() >= 18)) {
                mensajeDeErrorDeRegistro.setText("Debe ser mayor a 18 años");
            } else {
                try {
                    if (!Connect4.getSingletonConnect4().exitsNickName(usuarioNombreRegistro)) {
                        try {
                            Jugador1 = conecta4.registerPlayer(usuarioNombreRegistro, usuarioCorreoRegistro, usuarioContrasenaRegistro, nacimiento, 0);
                        } catch (Connect4DAOException e) {
                            e.printStackTrace();
                        }

                        Main.setRoot("Tablero");
                    } else {
                        mensajeDeErrorDeRegistro.setText("El usuario introducido ya existe");
                    }
                } catch (Connect4DAOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public void cargarImagenLocal() {
        String url = File.separator + "avatars" + File.separator + "avatar1.png";
        Image avatar = new Image(url);
        ImgVwavatar.imageProperty().setValue(avatar);
    }

    public void cargarImagenExterna() {
        String url = "c:" + File.separator + "avatars" + File.separator + "avatar1.png";

        Image avatar;
        try {
            avatar = new Image(new FileInputStream(url));
            ImgVwavatar.imageProperty().setValue(avatar);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void borrarJugador1() {
        Jugador1 = null;
    }

    public static void borrarJugador2() {
        Jugador2 = null;
    }

    public static Player getJugador1() {
        return Jugador1;
    }

    public static Player getJugador2() {
        return Jugador2;
    }

    public static void setJugador2(String nombre, String contrasena) {
        Jugador2 = conecta4.loginPlayer(nombre, contrasena);
    }


    public void resetearCamposInicioSesion() {
        usuarioInicioSesion.clear();
        contrasenaInicioSesion.clear();
        mensajeDeErrorDeInicioDeSesion.setText("");
    }

    public void resetearCamposRegistro() {
        usuarioRegistro.clear();
        contrasenaRegistro.clear();
        correoRegistro.clear();
        fechaNacimientoRegistro.getEditor().clear();
        mensajeDeErrorDeRegistro.setText("");
    }

    public void modoOscuroSwitch2() throws IOException {


        if (modoOscuro2.isArmed()) {
            modoOscuroRule2 = !modoOscuroRule2;
            if (modoOscuroRule2) {
                Main.loadStyleNight();
                modoOscuro2.setText("Modo claro");
            }else {
                Main.loadStyleDay();
                modoOscuro2.setText("Modo oscuro");
            }
        }
    }
}
