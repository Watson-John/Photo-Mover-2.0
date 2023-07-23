package com.photomover;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // So this code creates the window and loads the primary.fxml file
        scene = new Scene(loadFXML("primary2"), 600, 850);

        // This sets the title of the window
        stage.setTitle("Photo Mover");

        // make the window non-resizable
        stage.setResizable(false);

        // load css file
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        // scene.getStylesheets().add("styles.css");


        // This sets the scene to the stage
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }




    public static void main(String[] args) {
        launch();
    }

}