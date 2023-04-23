package io.github.klebbinhu.commandhandling;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface CommandHandler {

    void onCommand(SlashCommandInteractionEvent event);

}
