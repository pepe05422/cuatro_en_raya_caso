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

    private static TableroController tablero;


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("Tablero.fxml"));
        GridPane pantallaPrincipal = root.load();
        tablero = root.getController();
        tablero.controladorPrincipalTablero();

        Scene escena = new Scene(pantallaPrincipal);
        primaryStage.setScene(escena);
        primaryStage.setTitle("Conecta 4");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(598);
        primaryStage.setMaxHeight(598);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
