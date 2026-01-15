package org.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class that contains the main() to launch the JavaFX application.
 * @author Angel Vargas, Maxim Trofimchuk
 */
public class HelloApplication extends Application {
    /**
     * Sets the stage and scene and prepares everything to display the first view
     * @param stage stage to be used
     * @throws IOException exception to handle possible error where .fxml file does not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pizza-first-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        PizzaFirstController pizzaFirstController = fxmlLoader.getController();
        pizzaFirstController.setPrimaryStage(stage, scene);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * launches the application
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }
}