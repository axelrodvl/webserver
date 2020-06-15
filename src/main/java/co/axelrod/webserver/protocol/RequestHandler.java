package co.axelrod.webserver.protocol;

import java.io.IOException;

public interface RequestHandler<I extends Request, O extends Response> {
    O handleRequest(I request) throws IOException;
}
