package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.Interview;
import by.bsuir.entity.request.DeletAdminInterviewRequest;
import by.bsuir.entity.request.GetTimetableForAdminRequest;
import by.bsuir.entity.request.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminTimetableViewController extends SceneController implements Initializable  {
    @FXML
    public TableView<Interview> table;
    @FXML
    public TableColumn<Interview, String> dateColumn;
    @FXML
    public TableColumn<Interview, String> timeColumn;
    @FXML
    public TableColumn<Interview, String> aspirantNameColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        aspirantNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        Request request = new GetTimetableForAdminRequest(connection.getUser().getLogin());
        connection.sendRequest(request);
        List<Interview> interviews = (List<Interview>) answerHandler.waitForCollection(connection.getIn());
        table.getItems().addAll(interviews);
    }

    public void returnBack(ActionEvent actionEvent) {
        setScene(actionEvent,"/fxml/AdminMainMenu.fxml");
    }

    public void deleteInterview(ActionEvent actionEvent) {
        Interview selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Request request = new DeletAdminInterviewRequest(selectedItem);
            connection.sendRequest(request);
            List<Interview> intervies = (List<Interview>) answerHandler.waitForCollection(connection.getIn());
            table.getItems().clear();
            table.getItems().addAll(intervies);
        }
    }
}
