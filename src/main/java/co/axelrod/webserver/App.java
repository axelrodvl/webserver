package co.axelrod.webserver;

import co.axelrod.webserver.protocol.http.HttpProtocolHandler;
import co.axelrod.webserver.server.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static final int PORT = 8087;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ConnectionHandler(socket, httpProtocolHandler)).start();

        }
    }
}
