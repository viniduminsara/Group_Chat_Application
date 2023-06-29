package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientFormController{

    @FXML
    private Label lblName;

    @FXML
    private TextField txtMessage;

    @FXML
    private VBox vBox;

    BufferedReader bufferedReader;

    PrintWriter writer;

    @FXML
    void btnAttachmentOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        if (!txtMessage.getText().isEmpty()){
            writer.println(lblName.getText()+" : "+txtMessage.getText());
            txtMessage.clear();
        }
    }

    @FXML
    void txtMessageOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    public void initialize() {
        lblName.setText(LoginformController.username);

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",4277);

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);

                while (true){
                    String message = bufferedReader.readLine();
                    Platform.runLater(() -> {
                        vBox.getChildren().add(new Text(message));
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
