package io.github.klebbinhu;

import io.github.klebbinhu.commandhandling.CommandManager;
import io.github.klebbinhu.commands.*;
import io.github.klebbinhu.listeners.TestListener;
import io.github.klebbinhu.musicv2.MusicController;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class KlebbinhuBot {

    private final CommandManager commandManager;
    private final MusicController musicController = new MusicController();

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
        this.commandManager.register(new PlayCommand(this));
        this.commandManager.register(new QueueCommand(this));
        this.commandManager.register(new StopCommand(this));
        this.commandManager.register(new SkipCommand(this));

        this.commandManager.addAll();
    }

    public MusicController getMusicController() {
        return musicController;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}