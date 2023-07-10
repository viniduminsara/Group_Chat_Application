package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupFormController implements Initializable {
    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtEmplyeeId;

    @FXML
    private ImageView imageView;

    @FXML
    void btnImageChooserOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {

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
