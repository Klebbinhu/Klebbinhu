package io.github.klebbinhu.commandhandling;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.HashMap;

public class CommandManager {

    private final HashMap<String, CommandHandler> handlerMap = new HashMap<>();
    private final JDA jda;

    public CommandManager(JDA jda) {
        this.jda = jda;
        this.jda.addEventListener(new CommandListener(this));
    }
    public void register(SlashCommandData commandData, CommandHandler handler) {
        this.handlerMap.put(commandData.getName(), handler);
        this.jda.updateCommands().addCommands(commandData).queue();
    }

    public void register(AbstractCommand command) {
        register(command.getCommand(), command);
    }

    void dispatchCommand(String name, SlashCommandInteractionEvent event) {
        if (this.handlerMap.containsKey(name)) {
            CommandHandler handler = this.handlerMap.get(name);
            handler.onCommand(event);
        }
    }
}
