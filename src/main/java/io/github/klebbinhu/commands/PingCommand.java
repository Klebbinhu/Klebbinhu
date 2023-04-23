package io.github.klebbinhu.commands;

import io.github.klebbinhu.commandhandling.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class PingCommand extends AbstractCommand {

    @Override
    protected SlashCommandData getCommand() {
        return Commands.slash("ping", "Test ping command, just replies with a pong!");
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }
}
