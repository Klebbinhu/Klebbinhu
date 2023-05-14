package io.github.klebbinhu.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.KlebbinhuBot;
import io.github.klebbinhu.commandhandling.AbstractCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.*;
import java.util.Queue;

public class QueueCommand extends AbstractCommand {

    private final KlebbinhuBot klebbinhuBot;

    public QueueCommand(KlebbinhuBot bot) {
        this.klebbinhuBot = bot;
    }


    @Override
    protected SlashCommandData getCommand() {
        return Commands.slash("queue", "Fila de espera de músicas");
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Queue<AudioTrack> musicQueue = this.klebbinhuBot.getMusicController().getMusicQueue();
        if (musicQueue.isEmpty()) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Vazia!")
                    .setColor(Color.YELLOW)
                    .setDescription("A fila de músicas está vazia, adicione algumas com **/play**")
                    .build();
            event.getHook().editOriginalEmbeds(embed).queue();
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (this.klebbinhuBot.getMusicController().isPlaying()) {
            sb.append("\nA tocar: **")
                    .append(this.klebbinhuBot.getMusicController().getPlaying().getInfo().title)
                    .append("**");
        }
        int position = 1;
        for (AudioTrack track : musicQueue) {
            if (position > 10)
                break;
            sb.append(position)
                    .append(". ")
                    .append(track.getInfo().title)
                    .append("\n");
            position++;
        }
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Fila de músicas")
                .setColor(Color.WHITE)
                .setDescription(sb.toString())
                .build();
        event.getHook().editOriginalEmbeds(embed).queue();
    }
}
