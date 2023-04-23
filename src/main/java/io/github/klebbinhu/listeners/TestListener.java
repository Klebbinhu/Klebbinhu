package io.github.klebbinhu.listeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
}
