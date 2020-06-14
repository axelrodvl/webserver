package co.axelrod.webserver.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler {
    private static final String ROOT_PATH = "/Users/vadim/work/webserver/src/main/resources/html";

    public static byte[] getFileByPath(String path) throws IOException {
        return Files.readAllBytes(Path.of(ROOT_PATH + path));
    }
}
