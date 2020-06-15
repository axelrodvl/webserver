package co.axelrod.webserver;

import co.axelrod.webserver.impl.http.server.HttpServer;

public class WebServer {
    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_ROOT_PATH = "html";

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(getPort(args), getRootPath(args));
        httpServer.start();
    }

    private static int getPort(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = DEFAULT_PORT;
        }
        return port;
    }

    private static String getRootPath(String[] args) {
        String rootPath;
        if (args.length > 1) {
            rootPath = args[1];
        } else {
            rootPath = DEFAULT_ROOT_PATH;
        }
        return rootPath;
    }
}
