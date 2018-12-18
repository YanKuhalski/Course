package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.User;
import by.bsuir.entity.request.GetAllAdminsInfoRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HrOverviewSceneController extends SceneController implements Initializable {
    @FXML
    public TableColumn<User, String> nameColumn;
    @FXML
    public TableColumn<User, String> emailColumn;
    @FXML
    public TableView<User> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        Connection connection = Connection.getInstance();
        connection.sendRequest(new GetAllAdminsInfoRequest());
        AnswerHandler answerHandler = new AnswerHandler();
        List<User> hrs = (List<User>) answerHandler.waitForCollection(connection.getIn());
        table.getItems().addAll(hrs);
    }

    public void returnBack(ActionEvent event) {
        setScene(event,"/fxml/UserMainMenu.fxml");
    }
}
