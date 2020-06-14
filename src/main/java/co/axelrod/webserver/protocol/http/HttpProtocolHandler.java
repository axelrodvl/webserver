package co.axelrod.webserver.protocol.http;

import co.axelrod.webserver.protocol.ProtocolHandler;
import co.axelrod.webserver.server.RequestHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpProtocolHandler implements ProtocolHandler {
    private static final String CRLF = "\r\n";

    @Override
    public void processRequest(InputStream in, OutputStream out) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String message = bufferedReader.readLine();

        String path = message.split(" ")[1];
        path = path.split("\\?")[0];
        if ("/".equals(path)) {
            path = "/index.html";
        }

        while (!message.isBlank()) {
            message = bufferedReader.readLine();
        }

        byte[] responseBody = RequestHandler.getFileByPath(path);

        out.write(
                ("HTTP/1.1 200 OK" + CRLF
                + "Content-Length: " + responseBody.length + CRLF
                + CRLF).getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }
}
