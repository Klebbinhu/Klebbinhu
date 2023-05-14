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

public class StopCommand extends AbstractCommand {

    private final MusicController musicController;

    public StopCommand(KlebbinhuBot klebbinhuBot) {
        this.musicController = klebbinhuBot.getMusicController();
    }

    @Override
    protected SlashCommandData getCommand() {
        return Commands.slash("stop", "Para a musica que estiver a tocar atualmente!");
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        if (!this.musicController.isPlaying()) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Erro!")
                    .setColor(Color.RED)
                    .setDescription("Não existe nenhuma música a tocar atualmente!")
                    .build();
            event.getHook().editOriginalEmbeds(embed).queue();
            return;
        }
        AudioTrack playing = this.musicController.getPlaying();
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Sucesso!")
                .setColor(Color.GREEN)
                .setDescription("Música **" + playing.getInfo().title + "** parada com sucesso!")
                .build();
        event.getHook().editOriginalEmbeds(embed).queue();
        this.musicController.stop();
    }
}
