package io.github.klebbinhu;

import io.github.klebbinhu.webpanel.HttpServer;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public final class KlebbinhuMain {

    private static class CommandLineOptions {
        private boolean devMove = false;
    }

    public static void main(String[] args) {
        String botToken = readBotToken();
        if (botToken == null) {
            System.err.println("Couldn't read bot token! Closing application...");
            System.exit(1);
        }
        CommandLineOptions options = parseMainArgs(args);
        System.out.println("Starting Klebbinhu bot...");
        KlebbinhuBot bot = new KlebbinhuBot(botToken);
        try {
            bot.start();
        } catch (InterruptedException e) {
            System.exit(2);
        }
        System.out.println("Klebbinhu bot started!");
        System.out.println("Starting http server...");
        HttpServer server = new HttpServer(8080, options.devMove);
        server.start();
        System.out.println("Http server started!");
    }

    private static String readBotToken() {
        Class<?> clazz = KlebbinhuMain.class;
        URL url = clazz.getResource("/token.txt");
        if (url != null) {
            try (Scanner reader = new Scanner(url.openStream())) {
                return reader.nextLine();
            } catch (IOException e) {
                System.err.println("Failed to get InputStream of token file!");
            }
        }
        return null;
    }

    private static CommandLineOptions parseMainArgs(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        for (String arg : args) {
            if (arg.equals("-dev"))
                options.devMove = true;
        }
        return options;
    }
}
