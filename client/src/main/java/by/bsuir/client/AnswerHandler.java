package by.bsuir.client;

import by.bsuir.entity.Interview;
import by.bsuir.entity.Test;
import by.bsuir.entity.TestResult;
import by.bsuir.entity.User;
import by.bsuir.entity.request.EnterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class AnswerHandler {
    public String waitForAuthorizationResult(ObjectInputStream in) {
        try {
            EnterRequest enterRequest = (EnterRequest) in.readObject();
            User user = enterRequest.getUser();
            String role = user.getRole();
            Connection.getInstance().setUser(user);
            switch (role) {
                case "admin":
                    return "/fxml/AdminMainMenu.fxml";

                case "user":
                    return "/fxml/UserMainMenu.fxml";
                default:
                    JOptionPane.showMessageDialog(null,"Не верный логин или пароль");
                    return null;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<?> waitForCollection(ObjectInputStream in) {
        List<?> collection = new ArrayList<>();
        try {
            collection.addAll((Collection) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return collection;
    }

    public boolean waitForBool(ObjectInputStream in) {
        Boolean result = false;
        try {
            result = (Boolean) in.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<?, ?> waitForMap(ObjectInputStream in) {
        Map<?, ?> map = new HashMap<>();
        try {

            map.putAll((Map) in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Test waitForTest(ObjectInputStream in) {
        try {
            return (Test) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
