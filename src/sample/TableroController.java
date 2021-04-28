package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TableroController extends Application {

    private static final int columnas = 7;
    private static final int filas = 6;
    private static final double radio = 60.0;
    private Ficha [][] tablero = new Ficha[6][7];

    private boolean turnoJugador = true;
    private boolean insertarCirculo = true;



    @FXML
    public GridPane pantallaPrincipal;
    @FXML
    public VBox espacioJuego;
    @FXML
    public Label gameMode;
    @FXML
    public Label gamePlayer;

    private void crearContenido() {

        Shape gridVbox = crearGridVbox();
        List<Rectangle> rectagleList = craerColumnasClick();
        for (Rectangle rectangle:rectagleList) {
            pantallaPrincipal.add(rectangle, 0, 1);
        }

    }

    private List<Rectangle> craerColumnasClick() {
        List<Rectangle> rectangleList=new ArrayList<>();
        for (int j = 0; j < columnas; j++){
            Rectangle rectangle=new Rectangle(radio,(filas+1)*radio);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(j*(radio+5)+radio/4);

            rectangle.setOnMouseEntered(event -> rectangle.setFill(Color.valueOf("#eeeeee66")));
            rectangle.setOnMouseExited(event -> rectangle.setFill(Color.TRANSPARENT));

            final int column=j;//because of lambda expression
            rectangle.setOnMouseClicked(event -> {

            });
            rectangleList.add(rectangle);
        }

        return rectangleList;
    }

    private Shape crearGridVbox() {
        Shape gridVbox = new Rectangle((columnas+1)*radio, (filas+1)*radio);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Circle circulo = new Circle();
                circulo.setRadius(radio);
                circulo.setCenterX(radio);
                circulo.setCenterY(radio);
                circulo.setSmooth(true);
                circulo.setTranslateX(j*(radio+5)+radio/4);
                circulo.setTranslateY(i*(radio+5)+radio/4);
            }
        }
        gridVbox.setFill(Color.WHITE);
        return gridVbox;
    }


    public void chooseMulti(ActionEvent actionEvent) {
    }

    public void choosePC(ActionEvent actionEvent) {
    }

    public void addCircle(MouseEvent mouseEvent) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private static class Ficha extends Circle {

        private final boolean control;

        public Ficha(boolean control) {

            super(radio, control ? Color.rgb(14,19,24) : Color.rgb(30,67,53));
            this.control = control;
            setCenterX(radio);
            setCenterY(radio);
        }
    }
}
