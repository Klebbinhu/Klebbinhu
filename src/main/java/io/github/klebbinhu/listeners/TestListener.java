package io.github.klebbinhu.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class TestListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getAuthor().isBot() ||!e.isFromGuild()){
            return;
        }
        Message message = e.getMessage();
        String content = message.getContentRaw();

        if(content.equals("!play")){
            Guild guild = e.getGuild();
            VoiceChannel vc = guild.getVoiceChannelById("952948011548438598");
            AudioManager am = guild.getAudioManager();
            am.openAudioConnection(vc);
        }

        if (content.equals("!ping")){
            MessageChannel channel = e.getChannel();
            channel.sendMessage("pong!").queue();
        }
        if(content.equals("Feio")){
            MessageChannel channel = e.getChannel();
            channel.sendMessage("Tu es").queue();
        }
    }

}
