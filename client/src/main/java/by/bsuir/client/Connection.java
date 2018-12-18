package by.bsuir.client;

import by.bsuir.entity.User;
import by.bsuir.entity.request.CloseRequest;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private static int port = 29000;
    private static String HOST = "127.0.0.1";
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private static Connection instance;
    private User user = null;

    private Connection() {
        openConnection();
    }

    public static Connection getInstance() {
        if (instance == null) {
            return instance = new Connection();
        } else {
            return instance;
        }
    }

    private void openConnection() {
        try {
            socket = new Socket(HOST, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Сервер не доступен");
        }
    }

    public void closeConnection() {
        try {
            if (out != null && in != null && socket != null) {
                sendRequest(new CloseRequest());
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void sendRequest(Object object) {
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Соединение с сервером отсутствует");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}