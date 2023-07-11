package lk.ijse.chatApp.util;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class SystemAlert extends Alert{

    public SystemAlert(Alert.AlertType alertType, String title, String message, ButtonType... buttonTypes) {
        super(alertType, message ,buttonTypes);
        setTitle(title);

        String imagePath = null;
        switch (alertType){
            case WARNING:
                imagePath = "img/warning.png";
                break;
            case CONFIRMATION:
                imagePath = "img/confirmation.png";
                break;
        }

        if (imagePath != null){
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(120);
            imageView.setFitHeight(100);

            StackPane graphicPane = new StackPane(imageView);
            graphicPane.setAlignment(Pos.CENTER);
            graphicPane.getStyleClass().add("stackpane");
            getDialogPane().setHeader(graphicPane);
        }

        centerButtons(getDialogPane());

        getDialogPane().getStylesheets().add(getClass().getResource("/style/alert.css").toExternalForm());
        getDialogPane().getStyleClass().add("dialog-pane");
    }

    private void centerButtons(DialogPane dialogPane) {
        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
        hboxDialogPane.getChildren().add(spacer);
    }
}
