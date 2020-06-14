package co.axelrod.webserver.protocol.http.request;

import co.axelrod.webserver.protocol.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private HttpMethod httpMethod;

    private String absolutePath;
    private String query;

    private String httpVersion;

    private byte[] messageBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();

        parseRequestLine(line);

        while (!line.isBlank()) {
            line = bufferedReader.readLine();
        }
    }

    private void parseRequestLine(String line) {
        String[] parts = line.split(" ");
        httpMethod = HttpMethod.valueOf(parts[0]);
        parseRequestUri(parts[1]);
        httpVersion = parts[2];
    }

    private void parseRequestUri(String requestUri) {
        String[] parts = requestUri.split("\\?");
        this.absolutePath = parts[0];
        if(parts.length > 1) {
            this.query = parts[1];
        }
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getQuery() {
        return query;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }
}
