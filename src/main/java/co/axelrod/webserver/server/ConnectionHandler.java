package co.axelrod.webserver.server;

import co.axelrod.webserver.protocol.ProtocolHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final ProtocolHandler protocolHandler;

    public ConnectionHandler(Socket socket, ProtocolHandler protocolHandler) {
        this.socket = socket;
        this.protocolHandler = protocolHandler;
    }

    @Override
    public void run() {
        try (
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
        ) {
            protocolHandler.processRequest(in, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
