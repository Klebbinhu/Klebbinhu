package io.github.klebbinhu.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class testListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getAuthor().isBot()){
            return;
        }

        Message message = e.getMessage();
        String content = message.getContentRaw();
        if (content.equals("!ping")){
            MessageChannel channel = e.getChannel();
            channel.sendMessage("pong!").queue();
        }
    }

}
