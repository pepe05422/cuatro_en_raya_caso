package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableroController implements Initializable {

    private static final int columnas = 7;
    private static final int filas = 6;
    private static final double radio = 80.0;
    private Ficha [][] tablero = new Ficha[filas][columnas];

    private boolean turnoJugador = true;
    private boolean turnoAI = true;

    private static String jugadorUno = "Jug1";
    private static String jugadorDos = "Jug2";

    private boolean puedoInsertar = true;

    private Shape espacioJuegoTablero;


    @FXML
    public GridPane pantallaPrincipal;
    @FXML
    public Pane espacioJuego;
    @FXML
    public VBox modoAntesJuego, menuJuego, modoEspera;
    @FXML
    public Label gameMode, gamePlayer;
    @FXML
    public Button modoMulti, modoPc;


    /** Metodo controlador del tablero generado en la parte izquierda de la ventana **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        espacioJuegoTablero = dibujarGridTablero(); // Creamos un objeto tipo Shape

        pantallaPrincipal.add(espacioJuegoTablero, 0, 1); //Añadimos a "pantallaPrincipal" -> GridPane el objeto anterior en la posicion (0, 1)

        List<Rectangle> recuadrosTablero = resaltarColumnas(); //Creamos una lista de objetos tipo Rectangle y las asociamos la metodo "resaltarColumnas()" en el bucle añadimos recuadro a cada posicion
        for (Rectangle recuadro:recuadrosTablero) {
            pantallaPrincipal.add(recuadro, 0, 1);
        }

        gamePlayer.setText(turnoJugador ? jugadorUno : jugadorDos);



    }



    /** Metodo para dibujar o formar la rejilla del tablero **/
    private Shape dibujarGridTablero() {

        Shape espacioJuegoTablero = new Rectangle((columnas+4)*radio, (filas+4)*radio); //

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Circle circulo = new Circle();
                circulo.setRadius(radio/2);
                circulo.setCenterX(radio/2);
                circulo.setCenterY(radio/2);
                circulo.setSmooth(true);
                circulo.setTranslateX(j*(radio+5)+radio*2);
                circulo.setTranslateY(i*(radio+5)+radio*2);
                espacioJuegoTablero = Shape.subtract(espacioJuegoTablero, circulo);
            }
        }
        espacioJuegoTablero.setFill(Color.rgb(104, 121, 128));
        return espacioJuegoTablero;
    }

    private List<Rectangle> resaltarColumnas() {
        List<Rectangle> recuadrosTablero=new ArrayList<>();
        for (int j = 0; j < columnas; j++){
            Rectangle recuadro = new Rectangle(radio,(filas + 4) * radio);
            recuadro.setFill(Color.TRANSPARENT);
            recuadro.setTranslateX(j * (radio + 5) + radio*2);

            recuadro.setOnMouseEntered(event -> recuadro.setFill(Color.valueOf("#eeeeee66")));
            recuadro.setOnMouseExited(event -> recuadro.setFill(Color.TRANSPARENT));

            final int columna=j; //because of lambda expression
            recuadro.setOnMouseClicked(event -> {
                if (puedoInsertar) {
                    puedoInsertar = true;
                    insertarFicha(new Ficha(turnoJugador), columna);
                }

            });
            recuadrosTablero.add(recuadro);
        }

        return recuadrosTablero;
    }

    private void insertarFichaAI() {
        int col;

        if (turnoAI) {
            col = ((int) Math.random() * columnas);

            insertarFicha(new Ficha(turnoAI), col);
            
        }

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
        ficha.setTranslateX(columna * (radio + 5) + radio*2);

        int filaActual = fila;
        TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.4), ficha);
        transicion.setToY(fila * (radio + 5) + radio*2);

        transicion.setOnFinished(event -> {
            puedoInsertar = true;

            if (juegoTerminado(filaActual, columna )) {
                juegoFinalizado();
                return;
            }

            turnoJugador = !turnoJugador;

            gamePlayer.setText(turnoJugador ? jugadorUno : jugadorDos);

        });

        transicion.play();

    }
    private boolean juegoTerminado(int fila, int columna) {
        //Vertical Points
        //A small example: player has inserted his last disc at row=2 , column=3
        //
        //index of each element present in column [row][column]:  0,3   1,3   2,3   3,3   4,3   5,3-->Poind2D
        //notice same column of 3.

        List<Point2D> fichasVertical = IntStream.rangeClosed(fila - 3, fila + 3)  //range of row values= 0,1,2,3,4,5
                .mapToObj(f-> new Point2D(f, columna))  //0,3  1,3  2,3   3,3  4,3  5,3 ==> Point2D  x,y
                .collect(Collectors.toList());

        List<Point2D> fichasHorizontal = IntStream.rangeClosed(columna - 3, columna + 3)
                .mapToObj(c-> new Point2D(fila, c))
                .collect(Collectors.toList());

        Point2D puntoPartida1 =new Point2D(fila - 3,columna + 3);
        List<Point2D> puntoDiagonal1 = IntStream.rangeClosed(0,6)
                .mapToObj(i-> puntoPartida1.add(i,-i))
                .collect(Collectors.toList());

        Point2D puntoPartida2 =new Point2D(fila - 3,columna - 3);
        List<Point2D> puntoDiagonal2 = IntStream.rangeClosed(0,6)
                .mapToObj(i-> puntoPartida2.add(i,i))
                .collect(Collectors.toList());


        boolean terminado=comprobarCombinacionCuatro(fichasVertical) || comprobarCombinacionCuatro(fichasHorizontal)
                || comprobarCombinacionCuatro(puntoDiagonal1)
                || comprobarCombinacionCuatro(puntoDiagonal2);

        return terminado;
    }

    private boolean comprobarCombinacionCuatro(List<Point2D> puntos) {
        int cadena = 0;

        for (Point2D punto: puntos) {

            int indiceFilaPorCadena = (int) punto.getX();
            int indiceColumnaPorCadena = (int) punto.getY();

            //getting disc at particular row and column
            Ficha ficha = fichaDisponible(indiceFilaPorCadena, indiceColumnaPorCadena);

            if (ficha != null && ficha.alguienEstaMoviendo == turnoJugador) {
                // if the last inserted Disc belongs to the current player
                cadena++;
                if (cadena == 4) {
                    return true;
                }
            } else {
                cadena = 0;
            }
        }

        return false;//as we havent got the combination
    }

    private void juegoFinalizado(){

        String ganador = turnoJugador ? jugadorUno : jugadorDos;
        System.out.println("Ganador es: " + ganador);

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect 4");
        alert.setHeaderText("El ganador es " + ganador);
        ButtonType noBtn =new ButtonType("Salir");
        alert.getButtonTypes().setAll(noBtn);


        Platform.runLater(()->{
            Optional<ButtonType> btnClicked= alert.showAndWait();

            if (btnClicked.isPresent() && btnClicked.get() == noBtn){
                Platform.exit();
                System.exit(0);
            }
        });

    }


    public  Ficha fichaDisponible(int fila, int columna) {

        if (fila >= filas || fila < 0 || columna >= columnas || columna < 0){
            return null;
        }
        return tablero[fila][columna];
    }








    public void chooseMulti(ActionEvent actionEvent) {
        if (modoMulti.isArmed()) {
            modoAntesJuego.setVisible(false);
            menuJuego.setVisible(true);
            turnoAI = false;
            gameMode.setText("Multijugador");
        }
    }

    public void choosePc(ActionEvent actionEvent) {
        if (modoPc.isArmed()) {
            System.out.println("Suka");
            modoAntesJuego.setVisible(false);
            menuJuego.setVisible(true);
            gameMode.setText("Ordenador");
            turnoAI = true;
            insertarFichaAI();
        }
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
