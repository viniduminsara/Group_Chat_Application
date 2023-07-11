package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.chatApp.dto.UserDTO;
import lk.ijse.chatApp.model.UserModel;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupFormController implements Initializable {

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtname;

    @FXML
    private ImageView imageView;

    File file;

    @FXML
    void btnImageChooserOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the image");
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtId.getScene().getWindow());
        if (file != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                imageView.setImage(new Image(fileInputStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnSignupOnAction(ActionEvent event) throws IOException {
        if (!(txtId.getText().isEmpty() || txtname.getText().isEmpty())) {
            try {
                String employeeId = txtId.getText();
                String username = txtname.getText();

                boolean isExists = UserModel.existsUser(employeeId, username);
                if (!isExists) {
                    boolean isSaved;
                    if (file != null) {
                        InputStream inputStream = new FileInputStream(file);
                        isSaved = UserModel.saveUser(new UserDTO(employeeId, username, inputStream));
                    }else {
                        isSaved = UserModel.saveUser(new UserDTO(employeeId, username, null));
                    }
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Signup successfully completed!").show();
                        btnBackOnAction(event);
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Account is already exists").show();
                }
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Please fill all details").show();
        }

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginform.fxml"))));
        stage.setTitle("Login");
        stage.getIcons().add(new Image("img/logo.png"));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImageView();
    }

    private void setImageView() {
        double cornerRadius = 20.0; // Set the desired corner radius
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(cornerRadius);
        clip.setArcHeight(cornerRadius);
        imageView.setClip(clip);
    }
}
