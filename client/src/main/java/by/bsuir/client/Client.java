package by.bsuir.client;

import by.bsuir.entity.request.CloseRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Connection connection = Connection.getInstance();
        primaryStage.setOnCloseRequest(event -> {


            connection.closeConnection();
        });
        primaryStage.setTitle("Система тестирования");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
