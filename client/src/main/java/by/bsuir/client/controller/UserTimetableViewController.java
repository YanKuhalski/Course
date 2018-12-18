package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.Interview;
import by.bsuir.entity.request.GetTimetableForUserRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserTimetableViewController extends SceneController implements Initializable {
    @FXML
    public TableView table;
    @FXML
    public TableColumn dateColumn;
    @FXML
    public TableColumn timeColum;
    @FXML
    public TableColumn hrColumn;
    @FXML
    public TableColumn emailColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColum.setCellValueFactory(new PropertyValueFactory<>("time"));
        hrColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("adminEmail"));
        Connection connection = Connection.getInstance();
        connection.sendRequest(new GetTimetableForUserRequest(connection.getUser().getLogin()));
        AnswerHandler answerHandler  = new AnswerHandler();
        List<Interview> interviews =(List<Interview>) answerHandler.waitForCollection(connection.getIn());
        table.getItems().addAll(interviews);
    }

    public void returnBack(ActionEvent event) {
        setScene(event, "/fxml/UserMainMenu.fxml");
    }
}
