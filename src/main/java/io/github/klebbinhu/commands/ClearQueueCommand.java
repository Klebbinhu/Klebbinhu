package io.github.klebbinhu.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.KlebbinhuBot;
import io.github.klebbinhu.commandhandling.AbstractCommand;
import io.github.klebbinhu.musicv2.MusicController;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.Queue;

public class ClearQueueCommand extends AbstractCommand {

    private final MusicController musicController;

    public ClearQueueCommand(KlebbinhuBot klebbinhuBot) {
        this.musicController = klebbinhuBot.getMusicController();
    }

    @Override
    protected SlashCommandData getCommand() {
        return Commands.slash("clearqueue", "Limpa todas as músicas da fila de espera");
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        Queue<AudioTrack> musicQueue = this.musicController.getMusicQueue();
        if (musicQueue.isEmpty()) {
            return;
        }
    }
}
