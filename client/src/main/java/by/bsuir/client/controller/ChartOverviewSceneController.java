package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.request.GetTestStatisticsRequest;
import by.bsuir.entity.request.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class ChartOverviewSceneController extends SceneController implements Initializable  {
    @FXML
    public BarChart<String, Number> chart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart.setTitle("Средний результат прохождения тестов");
        Connection connection = Connection.getInstance();
        Request request = new GetTestStatisticsRequest();
        connection.sendRequest(request);
        AnswerHandler answerHandler = new AnswerHandler();
        Map<String, Double> result = (Map<String, Double>) answerHandler.waitForMap(connection.getIn());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Cсредний результат");
        Set<String> keys = result.keySet();
        for (String key : keys) {
            series.getData().add(new XYChart.Data<>(key, result.get(key)));
        }

        chart.getData().addAll(series);
    }

    public void returnBack(ActionEvent actionEvent) {
        setScene(actionEvent,"/fxml/AdminMainMenu.fxml");
    }
}
