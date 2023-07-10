package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginformController {

    @FXML
    private JFXTextField txtUsername;

    public static String username = "";

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        if (!txtUsername.getText().isEmpty()){
            username = txtUsername.getText();
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/clientForm.fxml"))));
            stage.setTitle(username+"'s chat");
            stage.getIcons().add(new Image("img/logo.png"));
            stage.setOnCloseRequest(windowEvent -> ClientFormController.leaveChat());
            stage.show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Please enter the username").show();
        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @FXML
    void btnSignupOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signupForm.fxml"))));
        stage.setTitle("Sign up");
        stage.getIcons().add(new Image("img/logo.png"));
        stage.show();
    }
}
