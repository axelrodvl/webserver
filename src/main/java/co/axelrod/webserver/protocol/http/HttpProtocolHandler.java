package co.axelrod.webserver.protocol.http;

import co.axelrod.webserver.protocol.ProtocolHandler;
import co.axelrod.webserver.protocol.http.request.HttpRequest;
import co.axelrod.webserver.protocol.http.response.HttpResponse;
import co.axelrod.webserver.protocol.http.response.HttpStatus;
import co.axelrod.webserver.server.RequestHandler;

import java.io.*;

public class HttpProtocolHandler implements ProtocolHandler {
    @Override
    public void processRequest(InputStream in, OutputStream out) throws IOException {
        HttpRequest httpRequest = new HttpRequest(in);

        HttpResponse httpResponse = new HttpResponse(
                HttpStatus.OK,
                RequestHandler.getFileByPath(httpRequest.getAbsolutePath())
        );

        httpResponse.writeResponse(out);
    }
}
