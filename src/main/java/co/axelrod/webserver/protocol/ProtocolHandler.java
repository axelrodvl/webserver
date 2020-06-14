package co.axelrod.webserver.protocol;

import java.io.*;

public interface ProtocolHandler {
    void processRequest(InputStream in, OutputStream out) throws IOException;
}
