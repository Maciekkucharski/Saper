package saper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;


public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        SplitPane splitPane = fxmlLoader.load();
        Scene scene = new Scene(splitPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }





}
