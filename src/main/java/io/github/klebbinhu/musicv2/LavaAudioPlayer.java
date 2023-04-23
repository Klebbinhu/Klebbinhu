package io.github.klebbinhu.musicv2;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class LavaAudioPlayer extends AudioEventAdapter {

    private final AudioPlayer player;
    private final MusicScheduler scheduler;
    private final AudioPlayerManager playerManager;

    public LavaAudioPlayer(MusicScheduler scheduler) {
        this.scheduler = scheduler;
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(this.playerManager);
        this.player = this.playerManager.createPlayer();
        this.player.addListener(this);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (this.scheduler.hasNext())
            playNext();
    }

    public boolean isPlaying() {
        return this.player.getPlayingTrack() != null;
    }

    public void pause() {
        this.player.setPaused(true);
    }

    public void resume() {
        this.player.setPaused(false);
    }

    public boolean isPaused() {
        return this.player.isPaused();
    }

    public void stop() {
        this.player.stopTrack();
    }

    public AudioTrack getPlaying() {
        return this.player.getPlayingTrack();
    }

    public void playNext() {
        AudioTrack nextTrack = this.scheduler.next();
        this.player.playTrack(nextTrack);
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public AudioPlayerManager getPlayerManager() {
        return playerManager;
    }
}
