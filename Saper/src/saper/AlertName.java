package saper;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class AlertName {
    public String getName() throws Exception {
        AtomicBoolean wrongInput = new AtomicBoolean(false);

        Label tempLabel = new Label();

        Stage stage = new Stage();

        TextField textField = new TextField();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Podaj Imie");
        stage.setMinWidth(250);
        stage.setMinHeight(90);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Wrong input");
        alert.setContentText("you can only use letters or digits");

        Button nameButton = new Button("zapisz");
        nameButton.setOnAction(actionEvent -> {
            int iterator = 0;
            String tempName = "";
            while (true) {
                if (iterator > 0) {
                    AlertName wrongName = new AlertName();
                    try {
                        tempName = wrongName.getName();
                        wrongInput.set(true);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Controller.sprawdzDane(textField.getText());
                        break;
                    } catch (Exception e) {
                        alert.showAndWait();
                    }
                    iterator++;
                }
            }
            if (wrongInput.get()) {
                tempLabel.setText(tempName);
            } else {
                tempLabel.setText(textField.getText());
            }

            stage.close();
        });


        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(textField, nameButton, tempLabel);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);

        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(scene);
        stage.showAndWait();

        return tempLabel.getText();
    }
}
