package sample;

import DBAccess.Connect4DAOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Connect4;

import java.io.IOException;

public class Main extends Application {

    private static Scene Escena;
    private static TableroController tablero;


    @Override
    public void start(Stage primaryStage) throws IOException {
        Escena = new Scene(loadFXML("Tablero"),1200, 800);

        primaryStage.setScene(Escena);
        primaryStage.setTitle("Conecta 4");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Escena.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException{
        if (fxml == "RegisterMenu") {
            return loadRegistro();
        }
        if (fxml == "Tablero") {
            return loadTablero();
        }
        return null;
    }

    public static Parent loadRegistro() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RegisterMenu.fxml"));
        return fxmlLoader.load();
    }

    public static Parent loadTablero() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Tablero.fxml"));
        tablero = (TableroController) fxmlLoader.getController();
        tablero.crearContenido();
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
