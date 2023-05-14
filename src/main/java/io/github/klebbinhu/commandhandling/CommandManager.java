package io.github.klebbinhu.commandhandling;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {

    private final HashMap<String, CommandHandler> handlerMap = new HashMap<>();
    private final List<SlashCommandData> toRegister = new ArrayList<>();
    private final JDABuilder jdaBuilder;

    public CommandManager(JDABuilder jdaBuilder) {
        this.jdaBuilder = jdaBuilder;
        this.jdaBuilder.addEventListeners(new CommandListener(this));
    }

    public void register(SlashCommandData commandData, CommandHandler handler) {
        this.handlerMap.put(commandData.getName(), handler);
        toRegister.add(commandData);
    }

    public void register(AbstractCommand command) {
        register(command.getCommand(), command);
    }

    public void submit(JDA jda) {
        jda.updateCommands().addCommands(toRegister).queue();
    }

    void dispatchCommand(String name, SlashCommandInteractionEvent event) {
        if (this.handlerMap.containsKey(name)) {
            CommandHandler handler = this.handlerMap.get(name);
            handler.onCommand(event);
        }
    }
}
