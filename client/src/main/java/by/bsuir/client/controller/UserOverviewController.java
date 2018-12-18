package by.bsuir.client.controller;

import by.bsuir.client.AnswerHandler;
import by.bsuir.client.Connection;
import by.bsuir.entity.User;
import by.bsuir.entity.request.user.control.AddUserRequest;
import by.bsuir.entity.request.user.control.GetAllUserInfoRequest;
import by.bsuir.entity.request.Request;
import by.bsuir.entity.request.user.control.RemoveUserRequest;
import by.bsuir.entity.request.user.control.UpdateUserRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserOverviewController extends SceneController implements Initializable {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> role;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    public TextField newLoginField;
    @FXML
    public TextField newPasswordField;
    @FXML
    public TextField newEmailField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    public ChoiceBox<String> emailBox;
    private String oldNameToUpdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.getSelectionModel().selectFirst();
        emailBox.getSelectionModel().selectFirst();
        userTable.setRowFactory(e -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User item = row.getItem();
                    newLoginField.setText(item.getLogin());
                    newEmailField.setText(item.getEmail().split("@")[0]);
                    SingleSelectionModel<String> selectionModel = roleComboBox.getSelectionModel();
                    oldNameToUpdate = item.getLogin();
                    switch (item.getRole()) {
                        case "admin":
                            selectionModel.selectLast();
                            break;
                        case "user":
                            selectionModel.selectFirst();
                            break;
                        default:
                            selectionModel.selectFirst();
                            break;
                    }
                }
            });
            return row;
        });
        name.setCellValueFactory(new PropertyValueFactory<>("login"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Request request = new GetAllUserInfoRequest(Connection.getInstance().getUser());
        connection.sendRequest(request);
        List<User> userList = (List<User>) answerHandler.waitForCollection(connection.getIn());
        ObservableList<User> users = FXCollections.observableArrayList(userList);
        userTable.setItems(users);
    }

    public void addUser(ActionEvent actionEvent) {
        String login = newLoginField.getText();
        String role = roleComboBox.getValue();
        String password = newPasswordField.getText();
        String email = newEmailField.getText();
        if (login.matches("[a-zа-яА-ЯA-Z]+")) {
            if (email.matches("[0-9a-zA-Z]+")) {
                if (password.matches("[a-zA-Zа-яА-Я0-9]+")) {
                    User user = new User(login, role);
                    user.setPassword(password);
                    user.setEmail(email + emailBox.getValue());
                    Request request = new AddUserRequest(user);
                    connection.sendRequest(request);
                    if (answerHandler.waitForBool(connection.getIn())) {
                        refreshUserList(null);
                    } else {
                        String message = "Пользователь с таким email или login уже существует";
                        JOptionPane.showMessageDialog(null, message);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Значение пароля не подходит");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Значение почты не подходит");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Значение имени не подходит");
        }
    }

    public void refactorUser(ActionEvent actionEvent) {
        if (oldNameToUpdate != null) {
            String login = newLoginField.getText();
            String role = roleComboBox.getValue();
            String password = newPasswordField.getText();
            String email = newEmailField.getText();
            if (login.matches("[a-zа-яА-ЯA-Z]+")) {
                if (email.matches("[0-9a-zA-Z]+")) {
                    if (password.matches("[a-zA-Zа-яА-Я0-9]+")) {
                        User user = new User(login, role);
                        user.setPassword(password);
                        user.setEmail(email + emailBox.getValue());
                        Request request = new UpdateUserRequest(oldNameToUpdate, user);
                        oldNameToUpdate = null;
                        connection.sendRequest(request);
                        if (answerHandler.waitForBool(connection.getIn())) {
                            refreshUserList(null);
                        } else {
                            String message = "Обновите данные, пользователь с таким " +
                                    "email или login уже существует либо записи которую вывыбрали уже не существует";
                            JOptionPane.showMessageDialog(null, message);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Значение пароля не подходит");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Значение почты не подходит");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Значение имени не подходит");
            }
        } else {
            String message = "Сначала выберите запись для редактирования 2ным нажатием";
            JOptionPane.showMessageDialog(null, message);
        }
    }

    public void deleteUser(ActionEvent actionEvent) {
        User selectedItem = userTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Request request = new RemoveUserRequest(connection.getUser(), selectedItem);
            connection.sendRequest(request);
            List<User> users = (List<User>) answerHandler.waitForCollection(connection.getIn());
            ObservableList<User> items = userTable.getItems();
            items.clear();
            items.addAll(users);
        }
    }

    public void refreshUserList(ActionEvent actionEvent) {
        Request request = new GetAllUserInfoRequest(Connection.getInstance().getUser());
        connection.sendRequest(request);
        List<User> userList = (List<User>) answerHandler.waitForCollection(connection.getIn());
        ObservableList<User> items = userTable.getItems();
        items.clear();
        items.addAll(userList);
    }

    public void returnBack(ActionEvent actionEvent) {
        setScene(actionEvent,"/fxml/AdminMainMenu.fxml");
    }
}
