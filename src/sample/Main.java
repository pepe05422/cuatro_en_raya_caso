package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene Escena;

    // Este metodo permite cargar el FXML unicamente pasando como parametro el nombre del
    // archivo sin necesidad de la extension
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Este metodo permite cambiar de escena unicamente declarando el FXML root
    public static void setRoot(String fxml) throws IOException {
        Escena.setRoot(loadFXML(fxml));
        Escena.getStylesheets().add(Main.class.getResource("/styles/main.css").toExternalForm());

    }
    /**
     * Dos metodos para cargar y eliminar el archivo con estilos de Modo Noche/Dia
     * **/
    public static void loadStyleNight() throws IOException {
        Escena.getStylesheets().add(Main.class.getResource("/styles/nightMode.css").toExternalForm());

    }
    public static void loadStyleDay() throws IOException {
        Escena.getStylesheets().remove(Main.class.getResource("/styles/nightMode.css").toExternalForm());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Escena = new Scene(loadFXML("RegisterMenu"), 1200, 600);
        primaryStage.setScene(Escena);
        primaryStage.show();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1200);
    }

    public static void main(String[] args) {
        launch();
    }
}
