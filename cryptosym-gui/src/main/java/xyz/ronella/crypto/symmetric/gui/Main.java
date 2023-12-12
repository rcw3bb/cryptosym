package xyz.ronella.crypto.symmetric.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import xyz.ronella.crypto.symmetric.gui.common.Images;
import xyz.ronella.crypto.symmetric.gui.function.ApplicationTitle;
import xyz.ronella.trivial.command.Invoker;

public class Main extends Application {
    private static final String MAIN_UI_FILE = "cryptosym.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader loader = new FXMLLoader(classLoader.getResource(MAIN_UI_FILE));
        Parent root = loader.load();
        primaryStage.getIcons().add(Images.ICON);
        primaryStage.setTitle(Invoker.generate(new ApplicationTitle()));
        //PrintText.print("Processing Hello world");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}