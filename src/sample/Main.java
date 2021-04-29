package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private TableroController tablero;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader root = new FXMLLoader(getClass().getResource("Untitled1.fxml"));
        GridPane pantallaPrincipal = root.load();
        tablero = root.getController();
        tablero.crearContenido();


        Scene escena = new Scene(pantallaPrincipal);
        primaryStage.setScene(escena);
        primaryStage.setTitle("Connecta 4");
        primaryStage.setMaxWidth(1100.0);
        primaryStage.setMaxHeight(800.0);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
