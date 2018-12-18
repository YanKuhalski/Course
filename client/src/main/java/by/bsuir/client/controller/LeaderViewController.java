package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.TestResult;
import by.bsuir.entity.request.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderViewController  extends SceneController implements Initializable {
    @FXML
    private DatePicker dateOfInterviewPicker;
    @FXML
    private ComboBox<String> timeBox;
    @FXML
    private ComboBox<String> testNameBox;
    @FXML
    private TableView<TestResult> table;
    @FXML
    private TableColumn<TestResult, String> nameColumn;
    @FXML
    private TableColumn<TestResult, String> emailColumn;
    @FXML
    private TableColumn<TestResult, Double> resultColumn;
    private String lastSelectedTest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateOfInterviewPicker.setValue(LocalDate.of(2018, 12, 30));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        Request request = new GetTestNamesRequest();
        connection.sendRequest(request);
        List<String> testNames = (List<String>) answerHandler.waitForCollection(connection.getIn());
        testNameBox.getItems().addAll(testNames);
        testNameBox.getSelectionModel().selectFirst();
        timeBox.getSelectionModel().selectFirst();
    }

    public void findFirtFiveResult(ActionEvent actionEvent) {
        lastSelectedTest = testNameBox.getValue();
        List<TestResult> testResults = getAllResuilts(lastSelectedTest);
        table.getItems().clear();
        if (testResults.size() > 5) {
            for (int i = 0; i < 5; i++) {
                table.getItems().add(testResults.get(i));
            }
        } else {
            table.getItems().addAll(testResults);
        }
    }

    public void showAll(ActionEvent actionEvent) {
        table.getItems().clear();
        lastSelectedTest  = testNameBox.getValue();
        List<TestResult> testResults = getAllResuilts(lastSelectedTest);
        table.getItems().addAll(testResults);
    }

    public void deleteResult(ActionEvent actionEvent) {
        TestResult selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            connection.sendRequest(new DeleteResultRequest(lastSelectedTest, selectedItem));
            List<TestResult> testNames = (List<TestResult>) answerHandler.waitForCollection(connection.getIn());
            table.getItems().clear();
            table.getItems().addAll(testNames);
        }
    }

    public void addInterview(ActionEvent actionEvent) {
        TestResult selectedUser = table.getSelectionModel().getSelectedItem();

        String name = selectedUser.getName();
        String time = timeBox.getValue();
        LocalDate date = dateOfInterviewPicker.getValue();
        Request request = new AddInterviewRequest(name, time, date, connection.getUser().getId());
        connection.sendRequest(request);
        boolean isSuccess = answerHandler.waitForBool(connection.getIn());
        String msg = "Собеседование добавлено";
        if (isSuccess == false) {
            msg = "Ошибка , попробуйте изменить дату или время";
        }
        JOptionPane.showMessageDialog(null, msg);
    }

    public void returnBack(ActionEvent actionEvent) {
        setScene(actionEvent,"/fxml/AdminMainMenu.fxml");
    }

    private List<TestResult> getAllResuilts(String testName) {
        Request request = new GetAllResultsRequest(testName);
        connection.sendRequest(request);
        List<TestResult> results = (List<TestResult>) answerHandler.waitForCollection(connection.getIn());
        return results;
    }

    public void showUserTimeTable(ActionEvent actionEvent) {
        TestResult selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Request request = new GetTimetableForUserRequest(selectedItem.getName());
            connection.sendRequest(request);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/UserByAdminTimeTableView.fxml"));
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
}
