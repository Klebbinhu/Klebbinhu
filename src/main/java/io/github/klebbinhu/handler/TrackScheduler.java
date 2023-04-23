package io.github.klebbinhu.handler;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }
}
