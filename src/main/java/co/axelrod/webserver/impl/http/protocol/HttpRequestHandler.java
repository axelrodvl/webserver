package co.axelrod.webserver.impl.http.protocol;

import co.axelrod.webserver.protocol.RequestHandler;
import co.axelrod.webserver.impl.http.protocol.request.HttpRequest;
import co.axelrod.webserver.impl.http.protocol.response.HttpResponse;
import co.axelrod.webserver.impl.http.protocol.response.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpRequestHandler implements RequestHandler<HttpRequest, HttpResponse> {
    private static final String ROOT_PATH = "/Users/vadim/work/webserver/src/main/resources/html";

    public static byte[] getFileByPath(String path) throws IOException {
        path = normalizePath(path);
        return Files.readAllBytes(Path.of(ROOT_PATH + path));
    }

    private static String normalizePath(String path) {
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
