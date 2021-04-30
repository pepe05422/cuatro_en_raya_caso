package sample;

import DBAccess.Connect4DAOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Connect4;

import java.io.IOException;

public class Main extends Application {

    private static Scene Escena;
    //private static TableroController tablero;

    // Este metodo permite cargar el FXML unicamente pasando como parametro el nombre del
    // archivo sin necesidad de la extension
    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Este metodo permite cambiar de escena unicamente declarando el FXML root
    public static void setRoot(String fxml) throws IOException {
        Escena.setRoot(loadFXML(fxml));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Escena = new Scene(loadFXML("RegisterMenu"),1200, 800);
        primaryStage.setScene(Escena);
        primaryStage.show();
        primaryStage.setMinHeight(800); // ***Hay que ajustar la altura minima
        primaryStage.setMinWidth(1200);
    }

    public static void main(String[] args) {
        launch();
    }
}
