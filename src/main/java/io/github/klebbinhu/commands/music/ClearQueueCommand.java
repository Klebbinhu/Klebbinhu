package io.github.klebbinhu.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.KlebbinhuBot;
import io.github.klebbinhu.commandhandling.AbstractCommand;
import io.github.klebbinhu.music.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.*;
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
        event.deferReply().queue();
        Queue<AudioTrack> musicQueue = this.musicController.getMusicQueue();
        if (musicQueue.isEmpty()) {
            return;
        }
        int musicCount = musicQueue.size();
        this.musicController.clearScheduler();
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Fila limpa!")
                .setColor(Color.GREEN)
                .setDescription("A fila com **" + musicCount + "** músicas foi limpa!")
                .build();
        event.getHook().editOriginalEmbeds(embed).queue();
    }
}
