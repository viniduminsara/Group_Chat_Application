package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import lk.ijse.chatApp.dto.UserDTO;
import lk.ijse.chatApp.model.UserModel;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientFormController{

    @FXML
    private Label lblName;

    @FXML
    private TextField txtMessage;

    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane emojiPane;

    @FXML
    private Pane imagePane;

    @FXML
    private ImageView clickedImage;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane root;

    @FXML
    private Pane textArea;

    @FXML
    private Pane header;

    @FXML
    private ImageView themeView;

    private BufferedReader bufferedReader;

    private static PrintWriter writer;

    private static String username;

    private File file;

    private String finalName;

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
            txtMessage.setEditable(true);
            txtMessage.clear();
        }
    }

    @FXML
    void txtMessageOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    @FXML
    void btnEmojiOnAction(ActionEvent event) {
        emojiPane.setVisible(!emojiPane.isVisible());
    }

    @FXML
    void closeOnAction(MouseEvent event) {
        imagePane.setVisible(false);
    }

    public void initialize() {

        username = LoginformController.username;
        setDetails();
        setImageView();

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",4277);

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);

                writer.println("joi"+username+"~joining");

                while (true){
                    String receive = bufferedReader.readLine();
                    String[] split = receive.split("~");
                    String name = split[0];
                    String message = split[1];

                    String firstChars = "";
                    if (name.length() > 3) {
                        firstChars = name.substring(0, 3);
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        String[] imgs = name.split("img");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("joi")){
                        String[] imgs = name.split("joi");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        String[] imgs = name.split("lef");
                        finalName = imgs[1];
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        if (finalName.equalsIgnoreCase(username)){

                            File receiveFile = new File(message);
                            Image image = new Image(receiveFile.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                clickedImage.setImage(imageView.getImage());
                                imagePane.setVisible(true);
                            });

                            Text text = new Text("~ Me");
                            text.getStyleClass().add("send-text");

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);

                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setAlignment(Pos.CENTER_LEFT);
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            File receives = new File(message);
                            Image image = new Image(receives.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                clickedImage.setImage(imageView.getImage());
                                imagePane.setVisible(true);
                            });

                            Text text = new Text("~ "+finalName);
                            text.getStyleClass().add("receive-text");

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("joi")) {
                        if (finalName.equalsIgnoreCase(username)){
                            Label text = new Label("You have join the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else{
                            Label text = new Label(finalName+" has join the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        Label text = new Label(finalName+" has left the chat");
                        text.getStyleClass().add("left-text");
                        HBox hBox = new HBox();
                        hBox.getChildren().add(text);
                        hBox.setAlignment(Pos.CENTER);

                        Platform.runLater(() -> {
                            vBox.getChildren().add(hBox);

                            HBox hBox1 = new HBox();
                            hBox1.setPadding(new Insets(5, 5, 5, 10));
                            vBox.getChildren().add(hBox1);
                        });
                    } else{
                        if(name.equalsIgnoreCase(username)){
                            TextFlow tempFlow = new TextFlow();
                            Text text = new Text(message);
                            text.setStyle("-fx-fill: white");
                            text.setWrappingWidth(200);
                            tempFlow.getChildren().add(text);
                            tempFlow.setMaxWidth(150);

                            Text nameText = new Text("~ Me");
                            nameText.getStyleClass().add("send-text");
                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(nameText, tempFlow);

                            HBox hBox = new HBox(12);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxWidth(220);
                            hBox.setMaxHeight(50);
                            hBox.getChildren().add(vbox);
                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            TextFlow tempFlow = new TextFlow();
                            Text text = new Text(message);
                            text.setStyle("-fx-fill: white");
                            text.setWrappingWidth(200);
                            tempFlow.getChildren().add(text);
                            tempFlow.setMaxWidth(150);

                            Text nameText = new Text("~ "+name);
                            nameText.getStyleClass().add("receive-text");
                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(nameText, tempFlow);

                            HBox hBox = new HBox();
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxWidth(220);
                            hBox.setMaxHeight(50);
                            hBox.getChildren().add(vbox);

                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
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

    public static void leaveChat(){
        writer.println("lef"+username + "~leave");
    }

    @FXML
    void themeViewOnAction(MouseEvent event) {
        if(themeView.getImage().getUrl().equals(new Image("img/light.png").getUrl())){
            root.setStyle("-fx-background-color: #fefae0;");
            textArea.getStyleClass().removeAll("dark-text");
            textArea.getStyleClass().add("light-text");
            header.setStyle("-fx-background-color: #2a9a84;");
            themeView.setImage(new Image("img/dark.png"));
        }else{
            root.setStyle("-fx-background-color:  #2f3e46;");
            textArea.getStyleClass().removeAll("light-text");
            textArea.getStyleClass().add("dark-text");
            header.setStyle("-fx-background-color: transparent;");
            themeView.setImage(new Image("img/light.png"));;
        }
    }

    @FXML
    void grinningFaceEmojiOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE00");
        emojiPane.setVisible(false);
    }

    @FXML
    void grinningSquintingOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE06");
        emojiPane.setVisible(false);
    }

    @FXML
    void smilingFaceWithOpenHandsOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83E\uDD17");
        emojiPane.setVisible(false);
    }

    @FXML
    void grinningFaceWithSweatOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE05");
        emojiPane.setVisible(false);
    }

    @FXML
    void faceWithTearsOfJoyOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE02");
        emojiPane.setVisible(false);
    }

    @FXML
    void cryingFaceOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE22");
        emojiPane.setVisible(false);
    }

    @FXML
    void sunglassesFaceOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0E");
        emojiPane.setVisible(false);
    }

    @FXML
    void smilingFaceWithHeartEyesOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0D");
        emojiPane.setVisible(false);
    }

    @FXML
    void smilingFaceWithHornsOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE08");
        emojiPane.setVisible(false);
    }

    @FXML
    void thumbsUpOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDC4D");
        emojiPane.setVisible(false);
    }

    private void setImageView() {
        double cornerRadius = 20.0; // Set the desired corner radius
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(cornerRadius);
        clip.setArcHeight(cornerRadius);
        imageView.setClip(clip);
    }

    private void setDetails(){
        try {
            UserDTO user = UserModel.getUser(username);
            if (user != null) {
                lblName.setText(user.getUsername());
                if (user.getImage() != null){
                    imageView.setImage(new Image(user.getImage()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
