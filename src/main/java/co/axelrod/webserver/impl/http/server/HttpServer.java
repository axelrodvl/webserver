package co.axelrod.webserver.impl.http.server;

import co.axelrod.webserver.impl.http.protocol.HttpProtocolHandler;
import co.axelrod.webserver.impl.http.protocol.HttpRequestHandler;
import co.axelrod.webserver.server.ConnectionHandler;
import co.axelrod.webserver.server.Server;

import java.net.Socket;

public class HttpServer extends Server {
    public HttpServer(int port, String rootPath) {
        super(port, rootPath);
    }

    @Override
    public ConnectionHandler getConnectionHandler(Socket socket) {
        HttpRequestHandler httpRequestHandler = new HttpRequestHandler(rootPath);
        HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler(httpRequestHandler);
        return new ConnectionHandler(socket, httpProtocolHandler);
    }
}
