package io.github.klebbinhu;

import io.github.klebbinhu.commandhandling.CommandManager;
import io.github.klebbinhu.commands.*;
import io.github.klebbinhu.commands.music.PlayCommand;
import io.github.klebbinhu.commands.music.QueueCommand;
import io.github.klebbinhu.commands.music.SkipCommand;
import io.github.klebbinhu.commands.music.StopCommand;
import io.github.klebbinhu.listeners.TestListener;
import io.github.klebbinhu.music.MusicController;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class KlebbinhuBot {

    private final MusicController musicController = new MusicController();
    private final JDABuilder jdaBuilder;
    private final CommandManager commandManager;

    public KlebbinhuBot(String token) {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("Gacha"));
        this.jdaBuilder = jdaBuilder;
        this.commandManager = new CommandManager(jdaBuilder);
        registerListeners();
        registerCommands();
    }

    public void start() throws InterruptedException {
        JDA jda = this.jdaBuilder.build();
        jda.awaitReady();
        this.commandManager.submit(jda);
    }

    private void registerListeners() {
        this.jdaBuilder.addEventListeners(new TestListener());
    }

    private void registerCommands() {
        this.commandManager.register(new PingCommand());
        this.commandManager.register(new PlayCommand(this));
        this.commandManager.register(new QueueCommand(this));
        this.commandManager.register(new StopCommand(this));
        this.commandManager.register(new SkipCommand(this));
    }

    public MusicController getMusicController() {
        return musicController;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}