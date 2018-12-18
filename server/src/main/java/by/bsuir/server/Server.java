package by.bsuir.server;

import by.bsuir.server.reader.PropertieReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int port = 29000;

    public static void main(String[] args) {
        PropertieReader propertieReader = PropertieReader.getInstance();
        int portNumber = propertieReader.getPortNumber();
        new Thread(new ConsoleListner() ).start();
        try (ServerSocket server = new ServerSocket(portNumber)) {
            System.out.println("Server is started with port number " + portNumber);
            server.setReuseAddress(true);
            while (true) {
                Socket client = server.accept();
                System.out.println("New connection " + client.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
