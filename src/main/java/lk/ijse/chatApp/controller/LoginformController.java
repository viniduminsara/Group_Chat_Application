package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
            stage.show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Please enter the username").show();
        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }
}
