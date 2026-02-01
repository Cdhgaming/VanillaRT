package net.vanillart.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LockerGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Label label = new Label("VanillaRT Locker GUI (skeleton)");
        root.getChildren().add(label);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("VanillaRT Locker GUI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
