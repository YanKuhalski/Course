package by.bsuir.client.controller;

import javafx.event.ActionEvent;

public class UserMainMenuController extends SceneController {
    public void showContacts(ActionEvent actionEvent) {
        setScene(actionEvent, "/fxml/HrOverviewScene.fxml");
    }

    public void showUserTimetable(ActionEvent event) {
        setScene(event, "/fxml/UserTimetableView.fxml");
    }

    public void choiseTest(ActionEvent event) {
        setScene(event, "/fxml/TestChoiceScene.fxml");
    }
}
