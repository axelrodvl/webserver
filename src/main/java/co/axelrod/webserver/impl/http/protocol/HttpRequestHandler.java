package co.axelrod.webserver.impl.http.protocol;

import co.axelrod.webserver.protocol.RequestHandler;
import co.axelrod.webserver.impl.http.protocol.request.HttpRequest;
import co.axelrod.webserver.impl.http.protocol.response.HttpResponse;
import co.axelrod.webserver.impl.http.protocol.response.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpRequestHandler implements RequestHandler<HttpRequest, HttpResponse> {
    private final String rootPath;

    public HttpRequestHandler(String rootPath) {
        this.rootPath = rootPath;
    }

    public byte[] getFileByPath(String path) throws IOException {
        path = normalizePath(path);
        try {
            return Files.readAllBytes(
                    Paths.get(new URI("file:///" + rootPath + path))
            );
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private String normalizePath(String path) {
        return "/".equals(path) ? "/index.html" : path;
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) throws IOException {
        return new HttpResponse(
                HttpStatus.OK,
                getFileByPath(request.getAbsolutePath())
        );
    }
}
