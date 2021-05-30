package sample;

import DBAccess.Connect4DAOException;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableroController implements Initializable {

    private static final int columnas = 8;
    private static final int filas = 7;
    private static final double radio = 80.0;
    private Ficha[][] tablero = new Ficha[filas][columnas];

    private boolean turnoJugador = true;
    private boolean turnoAI = false;



    private static Player jugadorUno = RegisterMenu.getJugador1();
    private static Player jugadorDos;


    private static String nameJugadorUno = jugadorUno.getNickName();
    private static String nameJugadorDos = "ordenador";

    private boolean puedoInsertar = true;
    private boolean instertarAI = false;

    private Shape espacioJuegoTablero;


    @FXML
    public GridPane pantallaPrincipal;
    @FXML
    public Pane espacioJuego;
    @FXML
    public VBox modoAntesJuego;
    @FXML
    public GridPane menuJuego;
    @FXML
    public GridPane modoEspera;
    @FXML
    public Label gameMode, gamePlayer;
    @FXML
    public Button modoMulti, modoIA, cerrarSesion, cerrarSesion2;


    /**
     * Metodo controlador del tablero generado en la parte izquierda de la ventana
     **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Metodo que cambia la pantalla de la izquierda una vez se elige el modo de juego
     **/
    public void iniciarModoJuego() {
        espacioJuegoTablero = dibujarGridTablero(); // Creamos un objeto tipo Shape
        pantallaPrincipal.add(espacioJuegoTablero, 0, 0); //Añadimos a "pantallaPrincipal" -> GridPane el objeto anterior en la posicion (0, 1)

        List<Rectangle> recuadrosTablero = resaltarColumnas(); //Creamos una lista de objetos tipo Rectangle y las asociamos la metodo "resaltarColumnas()" en el bucle añadimos recuadro a cada posicion
        for (Rectangle recuadro : recuadrosTablero) {
            pantallaPrincipal.add(recuadro, 0, 0);
        }

        gamePlayer.setText(turnoJugador ? nameJugadorUno : nameJugadorDos);
    }


    /**
     * Metodo para dibujar o formar la rejilla del tablero
     **/
    private Shape dibujarGridTablero() {

        Shape espacioJuegoTablero = new Rectangle((columnas + 4) * radio, (filas + 4) * radio);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Circle circulo = new Circle();
                circulo.setRadius(radio / 2);
                circulo.setCenterX(radio / 2);
                circulo.setCenterY(radio / 2);
                circulo.setSmooth(true);
                circulo.setTranslateX(j * (radio + 5) + radio * 2);
                circulo.setTranslateY(i * (radio + 5) + radio * 2);
                espacioJuegoTablero = Shape.subtract(espacioJuegoTablero, circulo);
            }
        }
        espacioJuegoTablero.setFill(Color.TRANSPARENT);
        return espacioJuegoTablero;
    }

    /**
     * Metodo que selecciona la columna y llama al metodo de inserción de ficha por evento de Click
     **/
    private List<Rectangle> resaltarColumnas() {
        List<Rectangle> recuadrosTablero = new ArrayList<>();
        for (int j = 0; j < columnas; j++) {
            Rectangle recuadro = new Rectangle(radio, (filas + 4) * radio);
            recuadro.setFill(Color.TRANSPARENT);
            recuadro.setTranslateX(j * (radio + 5) + radio * 2);

            recuadro.setOnMouseEntered(event -> recuadro.setFill(Color.rgb(243, 189, 161, 0.2)));
            recuadro.setOnMouseExited(event -> recuadro.setFill(Color.TRANSPARENT));

            final int columna = j; //because of lambda expression
            recuadro.setOnMouseClicked(event -> {
                if (puedoInsertar) {
                    puedoInsertar = true;
                    try {
                        insertarFicha(new Ficha(turnoJugador), columna);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            recuadrosTablero.add(recuadro);
        }

        return recuadrosTablero;
    }

    /**
     * Metodo que inicia el insertado por Inteligencia Artificial
     **/
    private void insertarAI() {
        int colAI;
        colAI = ((int) (Math.random() * columnas));

        if (instertarAI) {
            instertarAI = true;
            try {
                turnoAI = !turnoAI;
                insertarFichaAI(new Ficha(turnoAI), colAI);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Insertado de fichas por Inteligencia Artificial
     **/
    private void insertarFichaAI(Ficha ficha, int columna) throws InterruptedException {
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
        ficha.setTranslateX(columna * (radio + 5) + radio * 2);

        int filaActual = fila;
        TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.15), ficha);
        transicion.setToY(fila * (radio + 5) + radio * 2);

        transicion.setOnFinished(event -> {
            instertarAI = true;

            if (juegoTerminado(filaActual, columna)) {
                juegoFinalizado();
                return;
            }

            if (instertarAI) {
                turnoJugador = true;
                turnoAI = false;
            }
            gamePlayer.setText(turnoAI ? nameJugadorUno : nameJugadorDos);


        });

        transicion.play();
        // INPUT LAG**
        TimeUnit.MILLISECONDS.sleep(125);

    }

    /**
     * Insertado de fichas por Click
     **/
    private void insertarFicha(Ficha ficha, int columna) throws InterruptedException {
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
        ficha.setTranslateX(columna * (radio + 5) + radio * 2);

        int filaActual = fila;
        TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.15), ficha);
        transicion.setToY(fila * (radio + 5) + radio * 2);

        transicion.setOnFinished(event -> {
            puedoInsertar = true;

            if (juegoTerminado(filaActual, columna)) {
                juegoFinalizado();
                return;
            }

            if (instertarAI == false && puedoInsertar == true) {
                turnoJugador = !turnoJugador;
            }


            if (turnoAI == false && instertarAI == true) {
                turnoAI = !turnoAI;
                turnoJugador = false;
                insertarAI();
            }


            gamePlayer.setText(turnoJugador ? nameJugadorUno : nameJugadorDos);

        });

        transicion.play();
        // INPUT LAG**
        TimeUnit.MILLISECONDS.sleep(125);

    }

    private boolean juegoTerminado(int fila, int columna) {
        List<Point2D> fichasVertical = IntStream.rangeClosed(fila - 3, fila + 3)
                .mapToObj(f -> new Point2D(f, columna))
                .collect(Collectors.toList());

        List<Point2D> fichasHorizontal = IntStream.rangeClosed(columna - 3, columna + 3)
                .mapToObj(c -> new Point2D(fila, c))
                .collect(Collectors.toList());

        Point2D puntoPartida1 = new Point2D(fila - 3, columna + 3);
        List<Point2D> puntoDiagonal1 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> puntoPartida1.add(i, -i))
                .collect(Collectors.toList());

        Point2D puntoPartida2 = new Point2D(fila - 3, columna - 3);
        List<Point2D> puntoDiagonal2 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> puntoPartida2.add(i, i))
                .collect(Collectors.toList());


        boolean terminado = comprobarCombinacionCuatro(fichasVertical) || comprobarCombinacionCuatro(fichasHorizontal)
                || comprobarCombinacionCuatro(puntoDiagonal1)
                || comprobarCombinacionCuatro(puntoDiagonal2);

        return terminado;
    }

    /**
     * Metodo que comprueba si la combinacion es un exito. Recibe una lista de tipo Point2D donde vienen las cooredenadas de cada ficha
     **/
    private boolean comprobarCombinacionCuatro(List<Point2D> puntos) {
        int cadena = 0;

        for (Point2D punto : puntos) {

            int indiceFilaPorCadena = (int) punto.getX();
            int indiceColumnaPorCadena = (int) punto.getY();

            // Marcamos si una ficha en concreto esta en una coordenada segun fila y columna
            Ficha ficha = fichaDisponible(indiceFilaPorCadena, indiceColumnaPorCadena);

            // Si la ultima ficha que se ha insertado pertence al ultimo jugador
            if (ficha != null && ficha.alguienEstaMoviendo == turnoJugador) {

                cadena++;
                if (cadena == 4) {
                    return true;
                }
            } else {
                cadena = 0;
            }
        }

        return false; // Si no hemos encontrado una combinacion ganadora
    }


    /**
     * Metodo que saca una ventana emergente una vez que un jugador saque una combinacion de 4
     **/
    private void juegoFinalizado() {

        String ganador = turnoJugador ? nameJugadorUno : nameJugadorDos;
        System.out.println("Ganador es: " + ganador);
        if (ganador.equals("ordenador")) {
            System.out.println("Ganador: " + ganador);;
        } else if(nameJugadorDos.equals("ordenador")) {
            System.out.println("Has ganado bro");
        } else if (ganador.equals(jugadorUno.getNickName())) {
            LocalDateTime fecha = LocalDateTime.now();
            try {
                RegisterMenu.conecta4.regiterRound(fecha, jugadorUno, jugadorDos);
            } catch (Connect4DAOException e) {
                e.printStackTrace();
            }
        } else {
            LocalDateTime fecha = LocalDateTime.now();
            try {
                RegisterMenu.conecta4.regiterRound(fecha, jugadorDos, jugadorUno);
            } catch (Connect4DAOException e) {
                e.printStackTrace();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect 4");
        alert.setHeaderText("El ganador es " + ganador);
        ButtonType noBtn = new ButtonType("Salir");
        alert.getButtonTypes().setAll(noBtn);


        Platform.runLater(() -> {
            Optional<ButtonType> btnClicked = alert.showAndWait();

            if (btnClicked.isPresent() && btnClicked.get() == noBtn) {
                Platform.exit();
                System.exit(0);
            }
        });

    }


    /**
     * Metodo que comprueba si una ficha puede ser insertada en un hueco determinado
     **/
    public Ficha fichaDisponible(int fila, int columna) {

        if (fila >= filas || fila < 0 || columna >= columnas || columna < 0) {
            return null;
        }
        return tablero[fila][columna];
    }

    /**
     * Controlador de botones de Modo de juego
     **/
    @FXML
    public void modoMultijugadorLocal(ActionEvent actionEvent) throws IOException {
        if (modoMulti.isArmed()) {
            try {
                modoEspera.setVisible(false);
                espacioJuego.setVisible(true);
                espacioJuego.setOpacity(1.0);
                modoAntesJuego.setVisible(false);
                menuJuego.setVisible(true);
                gameMode.setText("Multijugador");
                iniciarModoJuego();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void modoMultijugadorIA(ActionEvent event) throws IOException {
        if (modoIA.isArmed()) {
            try {
                modoEspera.setVisible(false);
                espacioJuego.setVisible(true);
                espacioJuego.setOpacity(1.0);
                modoAntesJuego.setVisible(false);
                menuJuego.setVisible(true);
                gameMode.setText("Ordenador");
                turnoAI = true;
                turnoJugador = false;
                instertarAI = true;
                nameJugadorDos = "ordenador";
                iniciarModoJuego();
                insertarAI();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void cerrarSesion(ActionEvent event) throws IOException {
        if (cerrarSesion.isArmed() || cerrarSesion2.isArmed()) {
            try {
                RegisterMenu jugador = new RegisterMenu();
                jugador.borrarJugador1();
                jugador.borrarJugador2();
                System.out.println("se vienen cositas");
                Main.setRoot("RegisterMenu");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Definir el nombre del jugador mediante un metodo set, dado que es un atributo estatico
     **/
    public void setJugadorDos(String name) {
        nameJugadorDos = name;
    }

    @FXML
    public void modificarPerfil() {
        try {
            Scene modPerfil = new Scene(Main.loadFXML("ModifyProfile"));
            Stage ventana = new Stage();
            ventana.setScene(modPerfil);
            ventana.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicio sesion de jugador 2
     **/
    @FXML
    public void inicioSesionJugador2() {
        StackPane inicioSesion = new StackPane();

        TextField usuario = new TextField();
        PasswordField contrasena = new PasswordField();
        Button iniciarSesion = new Button();
        Button cancelar = new Button();
        VBox cajaVertical = new VBox();
        HBox cajaHorizontal = new HBox();
        Label mensaje = new Label();


        cajaVertical.getChildren().add(usuario);
        cajaVertical.getChildren().add(contrasena);
        cajaVertical.getChildren().add(cajaHorizontal);
        cajaHorizontal.getChildren().add(iniciarSesion);
        cajaHorizontal.getChildren().add(cancelar);

        inicioSesion.getChildren().add(cajaVertical);

        cajaVertical.setPadding(new Insets(50));
        cajaVertical.setSpacing(10);
        cajaVertical.setAlignment(Pos.CENTER);

        usuario.setFocusTraversable(false);
        usuario.setPromptText("Nombre de usuario");

        contrasena.setFocusTraversable(false);
        contrasena.setPromptText("Contraseña");

        cajaHorizontal.setSpacing(10);
        iniciarSesion.setText("Iniciar sesión");
        cancelar.setText("Cancelar");

        Scene iniciar_sesion2 = new Scene(inicioSesion, 500, 350);
        Stage nuevaVentana = new Stage();

        nuevaVentana.setTitle("Iniciar Sesión Jugador 2");
        nuevaVentana.setScene(iniciar_sesion2);
        nuevaVentana.setMaxHeight(180);
        nuevaVentana.setMaxWidth(400);
        nuevaVentana.setMinHeight(180);
        nuevaVentana.setMinWidth(400);

        nuevaVentana.show();

        iniciarSesion.setOnMouseClicked(ev -> {


            RegisterMenu.setJugador2(usuario.getText(), contrasena.getText());
            if (RegisterMenu.getJugador2().checkCredentials(usuario.getText(), contrasena.getText())) {
                jugadorDos = RegisterMenu.getJugador2();
                setJugadorDos(jugadorDos.getNickName());
                nuevaVentana.close();
                mensaje.setText("Inicio de sesion satisfactorio");
            } else {
                RegisterMenu.borrarJugador2();
                mensaje.setText("Usuario o contraseña incorrectos");
            }
            cajaVertical.getChildren().add(mensaje);

        });
    }

    /**
     * Creamos un objeto de tipo Ficha que dependa del objeto principal Circulo
     **/

    private static class Ficha extends Circle {

        private final boolean alguienEstaMoviendo;

        public Ficha(boolean alguienEstaMoviendo) {

            this.alguienEstaMoviendo = alguienEstaMoviendo;
            setRadius(radio / 2);
            setFill(alguienEstaMoviendo ? Color.valueOf("#24303E") : Color.valueOf("#4CAA88"));
            setCenterX(radio / 2);
            setCenterY(radio / 2);
        }
    }
}
