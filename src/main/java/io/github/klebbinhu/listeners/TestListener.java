package io.github.klebbinhu.listeners;

import io.github.klebbinhu.music.MusicController;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;

public class TestListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getAuthor().isBot() ||!e.isFromGuild()){
            return;
        }
        Message message = e.getMessage();
        String content = message.getContentRaw();
        if(content.equals("Feio")){
            MessageChannel channel = e.getChannel();
            channel.sendMessage("Tu es").queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "play" -> {
                event.deferReply().queue();
                String link = event.getOption("link", OptionMapping::getAsString);
                Member member = event.getMember();
                if (member == null || event.getGuild() == null)
                    return;
                GuildVoiceState voiceState = member.getVoiceState();
                if (voiceState == null || voiceState.getChannel() == null) {
                    event.getHook().editOriginal("Error! You need to be in a voice channel to use this command.").queue();
                    return;
                }
                VoiceChannel voiceChannel = voiceState.getChannel().asVoiceChannel();
                MusicController.getInstance().queue(link, result -> {
                    switch (result) {
                        case FAILED -> event.getHook().editOriginal("Failed to load!").queue();
                        case NO_MATCH -> event.getHook().editOriginal("No match!").queue();
                        case SUCCESS -> {
                            Guild guild = event.getGuild();
                            AudioManager am = guild.getAudioManager();
                            am.setSendingHandler(MusicController.getInstance().getAudioHandler());
                            am.openAudioConnection(voiceChannel);
                            event.getHook().editOriginal("Started playing!").queue();
                        }
                    }
                });
            }
        }
    }
}
