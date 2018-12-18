package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.Interview;
import by.bsuir.entity.request.DeletAdminInterviewRequest;
import by.bsuir.entity.request.DeletUserInterviewRequest;
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

public class UserByAdminTimeTableViewController extends SceneController implements Initializable {
    @FXML
    private TableView<Interview> table;
    @FXML
    private TableColumn<Interview, String> dateColumn;
    @FXML
    private TableColumn<Interview, String> timeColumn;
    @FXML
    private TableColumn<Interview, String> hrNameColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Interview> interviews = (List<Interview>) answerHandler.waitForCollection(connection.getIn());
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        hrNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        table.getItems().addAll(interviews);
    }

    public void returnBack(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LeaderView.fxml"));
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

    public void removeInterview(ActionEvent actionEvent) {
        Interview selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Request request = new DeletUserInterviewRequest(selectedItem);
            connection.sendRequest(request);
            List<Interview> intervies = (List<Interview>) answerHandler.waitForCollection(connection.getIn());
            table.getItems().clear();
            table.getItems().addAll(intervies);
        }
    }
}
