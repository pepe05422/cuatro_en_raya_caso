package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class TableroController {

    private static final int columnas = 7;
    private static final int filas = 6;
    private static final int radio = 60;
    private int [][] tablero = new int[6][7];

    private boolean turnoJugador = true;
    private boolean insertarCirculo = true;

    private static final String circuloColor1="#24303E";
    private static final String circuloColor2="#4CAA88";


    @FXML
    public GridPane pantallaPrincipal;
    @FXML
    public VBox espacioJuego;
    @FXML
    public Label gameMode;
    @FXML
    public Label gamePlayer;


    

    public void chooseMulti(ActionEvent actionEvent) {
    }

    public void choosePC(ActionEvent actionEvent) {
    }

    public void addCircle(MouseEvent mouseEvent) {

    }
}
