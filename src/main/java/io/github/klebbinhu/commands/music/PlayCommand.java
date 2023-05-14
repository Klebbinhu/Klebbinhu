package io.github.klebbinhu.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.KlebbinhuBot;
import io.github.klebbinhu.commandhandling.AbstractCommand;
import io.github.klebbinhu.music.events.PlaylistMusicQueueEvent;
import io.github.klebbinhu.music.events.FailedMusicQueueEvent;
import io.github.klebbinhu.music.events.NoMatchMusicQueueEvent;
import io.github.klebbinhu.music.events.SingleMusicQueueEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.util.List;

public class PlayCommand extends AbstractCommand {

    private final KlebbinhuBot klebbinhuBot;

    public PlayCommand(KlebbinhuBot bot) {
        this.klebbinhuBot = bot;
    }

    @Override
    protected SlashCommandData getCommand() {
        return Commands.slash("play", "Plays music from links")
                .addOption(OptionType.STRING, "link", "The link for the music to be played", true);
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String link = event.getOption("link", OptionMapping::getAsString);
        Member member = event.getMember();
        Guild guild = event.getGuild();
        if (member == null || guild == null)
            return;
        GuildVoiceState voiceState = member.getVoiceState();
        if (voiceState == null || voiceState.getChannel() == null) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Erro!")
                    .setColor(Color.RED)
                    .setDescription("Precisas de estar num canal de voz para usar este comando!")
                    .build();
            event.getHook().editOriginalEmbeds(embed).queue();
            return;
        }
        VoiceChannel voiceChannel = voiceState.getChannel().asVoiceChannel();
        this.klebbinhuBot.getMusicController().queue(link, queueEvent -> {
            if (queueEvent instanceof NoMatchMusicQueueEvent) {
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Erro!")
                        .setColor(Color.RED)
                        .setDescription("Não foi possível encontra nenhuma música com " + link)
                        .build();
                event.getHook().editOriginalEmbeds(embed).queue();
            } else if (queueEvent instanceof FailedMusicQueueEvent) {
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Erro!")
                        .setColor(Color.RED)
                        .setDescription("Não foi possível obter a música: " + link)
                        .build();
                event.getHook().editOriginalEmbeds(embed).queue();
            } else {
                AudioManager audioManager = guild.getAudioManager();
                audioManager.setSelfDeafened(true);
                audioManager.setSendingHandler(this.klebbinhuBot.getMusicController().getAudioProvider());
                audioManager.openAudioConnection(voiceChannel);
                if (queueEvent instanceof SingleMusicQueueEvent singleQueueEvent) {
                    AudioTrack track = singleQueueEvent.getAudioTrack();
                    String title = track.getInfo().title;
                    EmbedBuilder builder = new EmbedBuilder()
                            .setTitle("Sucesso!")
                            .setColor(Color.GREEN);
                    if (singleQueueEvent.startedPlaying())
                        builder.setDescription("Agora a tocar **" +  title + "**!");
                    else {
                        int position = this.klebbinhuBot.getMusicController().getPositionInQueue(track) + 1;
                        builder.setDescription("Música **" + title + "** adicionada com sucesso à fila na posição **" + position + "**!");
                    }
                    MessageEmbed embed = builder.build();
                    event.getHook().editOriginalEmbeds(embed).queue();
                } else if (queueEvent instanceof PlaylistMusicQueueEvent playlistQueueEvent) {
                    String playlistName = playlistQueueEvent.getPlaylist().getName();
                    List<AudioTrack> tracks = playlistQueueEvent.getPlaylist().getTracks();
                    if (tracks.isEmpty()) {
                        MessageEmbed embed = new EmbedBuilder()
                                .setTitle("Erro!")
                                .setColor(Color.RED)
                                .setDescription("A playlist " + playlistName + " está vazia!")
                                .build();
                        event.getHook().editOriginalEmbeds(embed).queue();
                        return;
                    }
                    int musicCount = tracks.size();
                    MessageEmbed embed = new EmbedBuilder()
                            .setTitle("Sucesso!")
                            .setColor(Color.GREEN)
                            .setDescription("Playlist **" + playlistName + "** com **" + musicCount + "** músics adicionada com sucesso à fila!")
                            .build();
                    event.getHook().editOriginalEmbeds(embed).queue();
                }
            }
        });
    }
}
