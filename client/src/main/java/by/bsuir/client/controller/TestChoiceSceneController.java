package by.bsuir.client.controller;

import by.bsuir.entity.request.GetAvailableTestNamesRequest;
import by.bsuir.entity.request.GetTestRequest;
import by.bsuir.entity.request.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestChoiceSceneController extends SceneController implements Initializable {
    @FXML
    public ChoiceBox<String> box;
    @FXML
    public Button startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Request request = new GetAvailableTestNamesRequest(connection.getUser().getId());
        connection.sendRequest(request);
        List<String> names = (List<String>) answerHandler.waitForCollection(connection.getIn());
        if (names.isEmpty()) {
            startButton.setVisible(false);
        } else {
            box.getItems().addAll(names);
            box.getSelectionModel().selectFirst();
        }
    }

    public void startTest(ActionEvent event) {
        Request request = new GetTestRequest(connection.getUser().getId(), box.getValue());
        connection.sendRequest(request);
        setScene(event, "/fxml/TestScene.fxml");
    }

    public void returnBack(ActionEvent event) {
        setScene(event, "/fxml/UserMainMenu.fxml");
    }
}
