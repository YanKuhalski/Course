package by.bsuir.server;

import by.bsuir.entity.request.CloseRequest;
import by.bsuir.entity.request.Request;
import by.bsuir.entity.request.RequestHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clienSocket;

    public ClientHandler(Socket client) {
        clienSocket = client;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clienSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clienSocket.getInputStream())) {
            RequestHandler handler = new RequestHandler();
            while (!clienSocket.isClosed()) {
                Request request = (Request) in.readObject();
                if (request.getClass() != CloseRequest.class) {
                    Object answer = handler.handle(request);
                    out.writeObject(answer);
                }
                else {
                    in.close();
                    out.close();
                    clienSocket.close();
                    System.out.println("Connection was closed");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
