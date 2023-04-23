package io.github.klebbinhu.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class PlayerListener extends AudioEventAdapter {

    private final AudioPlayer audioPlayer;
    private final MusicScheduler musicScheduler;

    public PlayerListener(AudioPlayer audioPlayer, MusicScheduler musicScheduler) {
        this.audioPlayer = audioPlayer;
        this.musicScheduler = musicScheduler;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (!this.musicScheduler.isEmpty()) {
            AudioTrack next = this.musicScheduler.next();
            this.audioPlayer.playTrack(next);
        }
    }
}
