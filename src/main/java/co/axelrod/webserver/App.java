package co.axelrod.webserver;

import co.axelrod.webserver.impl.http.server.HttpServer;

public class App {
    private static final int PORT = 8087;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(PORT);
        httpServer.start();
    }
}
