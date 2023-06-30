package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;

public class ClientFormController{

    @FXML
    private Label lblName;

    @FXML
    private TextField txtMessage;

    @FXML
    private VBox vBox;

    private BufferedReader bufferedReader;

    private PrintWriter writer;

    private String username;

    private File file;

    @FXML
    void btnAttachmentOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the image");
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtMessage.getScene().getWindow());
        if (file != null){
            txtMessage.setText("1 image selected");
            txtMessage.setEditable(false);
        }
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        if (!txtMessage.getText().isEmpty()){
            if (file != null){
                writer.println("img"+lblName.getText()+"~"+file.getPath());
            }else {
                writer.println(lblName.getText() + "~" + txtMessage.getText());
            }
            txtMessage.clear();
        }
    }

    @FXML
    void txtMessageOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    public void initialize() {
        username = LoginformController.username;
        lblName.setText(username);

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",4277);

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);

                while (true){
                    String receive = bufferedReader.readLine();
                    String[] split = receive.split("~");
                    String name = split[0];
                    String message = split[1];
                    System.out.println(message);

                    String firstChars = "";
                    if (name.length() > 3) {
                        firstChars = name.substring(0, 3);
                    }

                    if (name.equalsIgnoreCase(username)){
                        if (firstChars.equalsIgnoreCase("img")){
                            File receiveFile = new File(message);
                            Image image = new Image(receiveFile.toURI().toString());
                            ImageView imageView2 = new ImageView(image);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(imageView2);
                            });
                        }else {
                            Platform.runLater(() -> {
                                vBox.getChildren().add(new Text("Me : " + message));
                            });
                        }
                    }else {
                        if(firstChars.equalsIgnoreCase("img")){
                            File receiveFile = new File(message);
                            Image image = new Image(receiveFile.toURI().toString());
                            ImageView imageView2 = new ImageView(image);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(imageView2);
                            });
                        }else {
                            Platform.runLater(() -> {
                                vBox.getChildren().add(new Text(name + " : " + message));
                            });
                        }
                    }
                    file = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
