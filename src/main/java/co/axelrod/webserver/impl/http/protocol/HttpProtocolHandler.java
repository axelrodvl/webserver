package co.axelrod.webserver.impl.http.protocol;

import co.axelrod.webserver.protocol.ProtocolHandler;
import co.axelrod.webserver.impl.http.protocol.request.HttpRequest;
import co.axelrod.webserver.impl.http.protocol.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpProtocolHandler implements ProtocolHandler {
    private final HttpRequestHandler httpRequestHandler;

    public HttpProtocolHandler(HttpRequestHandler httpRequestHandler) {
        this.httpRequestHandler = httpRequestHandler;
    }

    @Override
    public void processRequest(InputStream in, OutputStream out) throws IOException {
        HttpRequest httpRequest = new HttpRequest(in);

        HttpResponse httpResponse = httpRequestHandler.handleRequest(httpRequest);
        httpResponse.writeResponse(out);
    }
}
