package co.axelrod.webserver.protocol.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private static final String CRLF = "\r\n";

    private final String httpVersion = "HTTP/1.1";
    private final HttpStatus httpStatus;
    private final byte[] body;

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    private byte[] getStatusLine() {
        return (
                httpVersion + " " + httpStatus.getStatusCode() + " " + httpStatus.getReasonPhrase() + CRLF
        ).getBytes(StandardCharsets.UTF_8);
    }

    public void writeResponse(OutputStream out) throws IOException {
        out.write(getStatusLine());

        if(body != null) {
            out.write(getContentLength());
            out.write(CRLF.getBytes(StandardCharsets.UTF_8));
            out.write(body);
        }
    }

    private byte[] getContentLength() {
        return (
                "Content-Length: " + body.length + CRLF
        ).getBytes(StandardCharsets.UTF_8);
    }
}
