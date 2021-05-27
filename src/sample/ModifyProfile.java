package sample;

import DBAccess.Connect4DAOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Player;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifyProfile implements Initializable {

    @FXML
    private TextField contrasena;
    @FXML
    private TextField correo;

    @FXML
    private DatePicker cumpleanos;

    @FXML
    private ImageView avatar;

    @FXML
    private Label mensaje;

    @FXML
    private Button bModificar;



    @FXML
    public void modificar() {
        String pass = contrasena.getText();
        String mail = correo.getText();
        LocalDate birthday = cumpleanos.getValue();


        if (!Player.checkPassword(pass)) {
            mensaje.setText("La contraseña no es valida\nuna contraseña valida debe tener entre 8 y 20 car�cteres\nal menos una letra mayuscula y minuscula\nal menos un digito\ny contener un caracter especial como !@#$%&()-+=");
        } else if (!Player.checkEmail(mail)) {
            mensaje.setText("Correo no valido");
        } else if (!(LocalDate.now().minusYears(birthday.getYear()).getYear() >= 18)) {
            mensaje.setText("Debe ser mayor a 18 años");
        }

        try {
            RegisterMenu.getJugador1().setPassword(pass);
            RegisterMenu.getJugador1().setEmail(mail);
            RegisterMenu.getJugador1().setBirthdate(birthday);
        } catch (Connect4DAOException e) {
            e.printStackTrace();
        }
        mensaje.setText("Datos modificados con exito");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
