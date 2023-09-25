package application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

import javafx.event.ActionEvent;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
    }

    public void switchLogs(ActionEvent event){

    }
}
