package io.github.klebbinhu.webpanel;

import spark.Spark;

import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {

    private static final String WEBPANEL_PATH = "src/main/resources/webpanel";
    private final int port;

    public HttpServer(int port, boolean devMode) {
        if (devMode && Files.exists(Path.of(WEBPANEL_PATH)))
            Spark.staticFiles.externalLocation(WEBPANEL_PATH);
        else
            Spark.staticFiles.location("/webpanel");
        this.port = port;
    }

    public void start() {
        Spark.port(port);
        Spark.init();
        setup();
    }

    private void setup() {
        Spark.get("/testrequest", ((request, response) -> {
            return "Hello world!";
        }));
    }
}
