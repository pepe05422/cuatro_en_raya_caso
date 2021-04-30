package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import sample.RegisterMenu;

public class TableroController implements Initializable {

    private RegisterMenu usuario = new RegisterMenu();
    private static final int columnas = 7;
    private static final int filas = 6;
    private static final double radio = 80.0;
    private Ficha [][] tablero = new Ficha[filas][columnas];

    private boolean turnoJugador = true;
    private boolean puedoInsertar = true;



    @FXML
    public GridPane pantallaPrincipal;
    @FXML
    public Pane espacioJuego;
    @FXML
    public Label gameMode;
    @FXML
    public Label gamePlayer;
    @FXML
    private Button cerrarSesion;

    @FXML protected void CerrarSesion(ActionEvent event) throws IOException {
        if (cerrarSesion.isArmed()) {
            //usuario.borrarJugador1();
            Main.setRoot("RegisterMenu");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void crearContenido() {

        Shape gridVbox = crearGridVbox();

        pantallaPrincipal.add(gridVbox, 0, 1);

        List<Rectangle> rectangleList = craerColumnasClick();
        for (Rectangle rectangle:rectangleList) {
            pantallaPrincipal.add(rectangle, 0, 1);
        }

    }
    private Shape crearGridVbox() {
        Shape gridVbox = new Rectangle((columnas+1)*radio, (filas+1)*radio);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Circle circulo = new Circle();
                circulo.setRadius(radio/2);
                circulo.setCenterX(radio/2);
                circulo.setCenterY(radio/2);
                circulo.setSmooth(true);
                circulo.setTranslateX(j*(radio+5)+radio/4);
                circulo.setTranslateY(i*(radio+5)+radio/4);
                gridVbox = Shape.subtract(gridVbox, circulo);
            }
        }
        gridVbox.setFill(Color.rgb(104, 121, 128));
        return gridVbox;
    }

    private List<Rectangle> craerColumnasClick() {
        List<Rectangle> rectangleList=new ArrayList<>();
        for (int j = 0; j < columnas; j++){
            Rectangle rectangle=new Rectangle(radio,(filas+1)*radio);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(j*(radio+5)+radio/4);

            rectangle.setOnMouseEntered(event -> rectangle.setFill(Color.valueOf("#eeeeee66")));
            rectangle.setOnMouseExited(event -> rectangle.setFill(Color.TRANSPARENT));

            final int columna=j; //because of lambda expression
            rectangle.setOnMouseClicked(event -> {
                if (puedoInsertar) {
                    puedoInsertar = true;
                    insertarFicha(new Ficha(turnoJugador), columna);
                }

            });
            rectangleList.add(rectangle);
        }

        return rectangleList;
    }

    private void insertarFicha(Ficha ficha, int columna) {

        int fila = filas - 1;
        while (fila >= 0) {
            if (fichaDisponible(fila, columna) == null) {
                break;
            }
            fila--;
        }
        if (fila < 0) {
            System.out.println("No se pueden insertar mas fichas");
            return;
        }
        tablero[fila][columna] = ficha;
        espacioJuego.getChildren().add(ficha);
        ficha.setTranslateX(columna * (radio+5) + radio/4);

        int filaActual = fila;
        TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.4), ficha);
        transicion.setToY(fila * (radio + 5) + radio/4);

        transicion.play();

    }


    public  Ficha fichaDisponible(int fila, int columna) {

        if (fila >= filas || fila < 0 || columna >= columnas || columna < 0){
            return null;
        }
        return tablero[fila][columna];
    }


    public void chooseMulti(ActionEvent actionEvent) {
    }

    public void choosePC(ActionEvent actionEvent) {
    }

    public void addCircle(MouseEvent mouseEvent) {

    }

    public void start(Stage primaryStage) throws Exception {

    }

    private static class Ficha extends Circle {

        private final boolean alguienEstaMoviendo;

        public Ficha(boolean alguienEstaMoviendo) {

            this.alguienEstaMoviendo = alguienEstaMoviendo;
            setRadius(radio/2);
            setFill(alguienEstaMoviendo ? Color.valueOf("#24303E") : Color.valueOf("#4CAA88"));
            setCenterX(radio/2);
            setCenterY(radio/2);
        }
    }
}
