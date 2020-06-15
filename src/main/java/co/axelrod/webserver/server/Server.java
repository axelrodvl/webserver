package co.axelrod.webserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server {
    private volatile boolean running = true;

    protected final int port;
    protected final String rootPath;

    public Server(int port, String rootPath) {
        this.port = port;
        this.rootPath = rootPath;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                Socket socket = serverSocket.accept();
                new Thread(getConnectionHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract ConnectionHandler getConnectionHandler(Socket socket);

    public void setRunning(boolean running) {
        this.running = running;
    }
}
