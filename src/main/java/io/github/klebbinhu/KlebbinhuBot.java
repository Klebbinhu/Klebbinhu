package io.github.klebbinhu;

import io.github.klebbinhu.commandhandling.CommandListener;
import io.github.klebbinhu.commandhandling.CommandManager;
import io.github.klebbinhu.commands.PingCommand;
import io.github.klebbinhu.listeners.TestListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class KlebbinhuBot {

    private final CommandManager commandManager;

    public KlebbinhuBot(String token) {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("Gacha"));
        registerListeners(jdaBuilder);
        JDA jda = jdaBuilder.build();
        this.commandManager = new CommandManager(jda);
        registerCommands();
    }

    private void registerListeners(JDABuilder builder) {
        builder.addEventListeners(new TestListener());
    }

    private void registerCommands() {
        this.commandManager.register(new PingCommand());
    }
}