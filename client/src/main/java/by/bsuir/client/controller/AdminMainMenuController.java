package by.bsuir.client.controller;

import javafx.event.ActionEvent;

public class AdminMainMenuController extends SceneController {
    public void workWithUsers(ActionEvent actionEvent) {
        setScene(actionEvent, "/fxml/UserOverview.fxml");
    }

    public void workWithInterview(ActionEvent actionEvent) {
        setScene(actionEvent, "/fxml/LeaderView.fxml");
    }

    public void showAdminInterview(ActionEvent actionEvent) {
        setScene(actionEvent, "/fxml/AdminTimetableView.fxml");
    }

    public void showChart(ActionEvent actionEvent) {
        setScene(actionEvent, "/fxml/ChartOverviewScene.fxml");
    }
}
