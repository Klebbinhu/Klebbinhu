package io.github.klebbinhu;

import io.github.klebbinhu.listeners.TestListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class KlebbinhuBot {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Run the bot with the token as argument!");
            System.exit(1);
        }
        JDABuilder builder = JDABuilder.createDefault(args[0])
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("Gacha"));
        registerListener(builder);
        builder.build();
    }

    private static void registerListener(JDABuilder builder) {
        builder.addEventListeners(new TestListener());
    }
}