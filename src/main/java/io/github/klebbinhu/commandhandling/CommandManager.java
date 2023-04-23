package io.github.klebbinhu.commandhandling;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {

    private final HashMap<String, CommandHandler> handlerMap = new HashMap<>();
    private final List<SlashCommandData> toRegister = new ArrayList<>();
    private final JDA jda;

    public CommandManager(JDA jda) {
        this.jda = jda;
        this.jda.addEventListener(new CommandListener(this));
    }
    public void register(SlashCommandData commandData, CommandHandler handler) {
        this.handlerMap.put(commandData.getName(), handler);
//        this.jda.updateCommands().addCommands(commandData).queue();
        toRegister.add(commandData);
    }

    public void register(AbstractCommand command) {
        register(command.getCommand(), command);
    }

    public void addAll() {
        this.jda.updateCommands().addCommands(toRegister).queue();
    }

    void dispatchCommand(String name, SlashCommandInteractionEvent event) {
        if (this.handlerMap.containsKey(name)) {
            CommandHandler handler = this.handlerMap.get(name);
            handler.onCommand(event);
        }
    }
}
