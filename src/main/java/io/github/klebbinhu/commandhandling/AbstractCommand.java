package io.github.klebbinhu.commandhandling;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class AbstractCommand implements CommandHandler {
    protected AbstractCommand() {
    }

    protected abstract SlashCommandData getCommand();

}
