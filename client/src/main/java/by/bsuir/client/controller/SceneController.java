package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class SceneController {
    protected static final Connection connection;
    protected static final AnswerHandler answerHandler;

    static {
        connection = Connection.getInstance();
        answerHandler = new AnswerHandler();
    }

    protected void setScene(ActionEvent event, String fileName) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fileName));
        Parent load = null;
        try {
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(load);
        window.setScene(scene);
        window.show();
    }
}
