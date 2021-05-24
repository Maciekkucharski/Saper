package saper;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {

    protected void display(String title, String message){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);
        stage.setMinHeight(90);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("exit");
        Button restartButton = new Button("restart");
        closeButton.setOnAction(actionEvent -> {
            Platform.exit();
            System.exit(0);
        });
        restartButton.setOnAction(actionEvent -> {
            stage.close();
        });

        HBox hBox = new HBox(2);
        hBox.getChildren().addAll(closeButton,restartButton);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(label,hBox);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(scene);
        stage.showAndWait();

    }
}
