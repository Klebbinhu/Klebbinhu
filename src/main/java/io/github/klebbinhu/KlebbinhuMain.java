package io.github.klebbinhu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class KlebbinhuMain {

    public static void main(String[] args) {
        File tokenFile = new File("src/token.txt");
        if (!tokenFile.exists()) {
            System.err.println("No token file (token.txt) found!");
            System.exit(1);
        }
        try (Scanner reader = new Scanner(tokenFile)) {
            String token = reader.nextLine();
            KlebbinhuBot bot = new KlebbinhuBot(token);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to read token file!");
            e.printStackTrace();
        }
    }
}
