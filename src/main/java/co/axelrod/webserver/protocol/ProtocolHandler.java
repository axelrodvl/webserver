package co.axelrod.webserver.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ProtocolHandler {
    void processRequest(InputStream in, OutputStream out) throws IOException;
}
