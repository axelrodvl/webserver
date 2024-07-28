package co.axelrod.webserver.blog;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Template {
    public static String getTemplate(String path) {
        try {
            return new String(Files.readAllBytes(
                    Paths.get(new URI("file:///" + path + "/index.html"))
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
