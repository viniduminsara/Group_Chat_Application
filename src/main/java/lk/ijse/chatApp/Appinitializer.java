package lk.ijse.chatApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Appinitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginform.fxml"))));
        primaryStage.setTitle("Login");
        primaryStage.getIcons().add(new Image("img/logo.png"));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
