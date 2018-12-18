package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.request.EnterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EnterController extends SceneController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    public void enter(ActionEvent actionEvent) {
        Connection connection = Connection.getInstance();
        EnterRequest reqvest = new EnterRequest(login.getText(), password.getText());
        connection.sendRequest(reqvest);
        AnswerHandler answerHandler = new AnswerHandler();
        String sceneName = answerHandler.waitForAuthorizationResult(connection.getIn());
        if (sceneName != null) {
            setScene(actionEvent, sceneName);
        }
    }
}
