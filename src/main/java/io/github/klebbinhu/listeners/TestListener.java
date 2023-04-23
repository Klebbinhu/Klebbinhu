package io.github.klebbinhu.listeners;

import io.github.klebbinhu.handler.TestAudioHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
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
            case "ping" -> {
                event.deferReply().queue();
                MessageChannel channel = event.getChannel();
                channel.sendMessage("pong!").queue();
            }
            case "play" -> {
                event.deferReply().queue();
                String link = event.getOption("link", OptionMapping::getAsString);
                Guild guild = event.getGuild();
                VoiceChannel vc = guild.getVoiceChannelById("952948011548438598");
                AudioManager am = guild.getAudioManager();
                am.setSendingHandler(new TestAudioHandler(link));
                am.openAudioConnection(vc);
                event.getHook().editOriginal("Started playing!").queue();

            }
        }
    }
}
