package co.axelrod.webserver.impl.http.protocol.response;

import co.axelrod.webserver.protocol.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse extends Response {
    private static final String CRLF = "\r\n";

    private static final String HTTP_VERSION = "HTTP/1.1";
    private final HttpStatus httpStatus;

    private static final String SERVER = "axelrod-webserver/0.0.1";

    private final byte[] body;

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    private byte[] getStatusLine() {
        return (
                HTTP_VERSION + " " + httpStatus.getStatusCode() + " " + httpStatus.getReasonPhrase() + CRLF
        ).getBytes(StandardCharsets.UTF_8);
    }

    public void writeResponse(OutputStream out) throws IOException {
        out.write(getStatusLine());
        out.write(getHeader("Server", SERVER));

        if (body != null) {
            out.write(getContentLength());
            out.write(CRLF.getBytes(StandardCharsets.UTF_8));
            out.write(body);
        }
    }

    private byte[] getContentLength() {
        return getHeader("Content-Length", Integer.toString(body.length));
    }

    private byte[] getHeader(String name, String value) {
        return (
                name + ": " + value + CRLF
        ).getBytes(StandardCharsets.UTF_8);
    }
}
