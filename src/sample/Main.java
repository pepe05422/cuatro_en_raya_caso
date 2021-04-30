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
    private static TableroController tablero;


    @Override
    public void start(Stage primaryStage) throws IOException {
        Escena = new Scene(loadFXML("Tablero"),1200, 800);
        primaryStage.setScene(Escena);
        primaryStage.setTitle("Conecta 4");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(598);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Escena.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        GridPane pantallaPrincipal = fxmlLoader.load();
        tablero.crearContenido();
        return pantallaPrincipal;
    }

    public static void main(String[] args) {
        launch();
    }
}
