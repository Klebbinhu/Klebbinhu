package io.github.klebbinhu.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import io.github.klebbinhu.music.events.*;

import java.util.function.Consumer;

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
        if (this.scheduler.hasNext() && endReason == AudioTrackEndReason.FINISHED)
            playNext();
    }

    public void queue(String identifier, Consumer<MusicQueueEvent> callBack) {
        this.playerManager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                boolean startedPlaying = player.startTrack(audioTrack, true);
                if (!startedPlaying)
                    scheduler.queue(audioTrack);
                callBack.accept(new SingleMusicQueueEvent(audioTrack, startedPlaying));
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                for (AudioTrack track : audioPlaylist.getTracks()) {
                    if (!player.startTrack(track, true))
                        scheduler.queue(track);
                }
                callBack.accept(new PlaylistMusicQueueEvent(audioPlaylist));
            }

            @Override
            public void noMatches() {
                callBack.accept(new NoMatchMusicQueueEvent());
            }

            @Override
            public void loadFailed(FriendlyException e) {
                callBack.accept(new FailedMusicQueueEvent());
            }
        });
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
        this.player.startTrack(nextTrack, false);
    }

    public AudioPlayer getPlayer() {
        return player;
    }
}
