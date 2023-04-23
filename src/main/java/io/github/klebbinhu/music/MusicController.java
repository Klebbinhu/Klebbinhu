package io.github.klebbinhu.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.function.Consumer;

public class MusicController {

    private static MusicController instance;

    private final AudioPlayer audioPlayer;
    private final MusicScheduler musicScheduler;
    private final AudioHandler audioHandler;
    private final AudioPlayerManager playerManager;

    private MusicController() {
        this.musicScheduler = new MusicScheduler();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(this.playerManager);
        this.audioPlayer = this.playerManager.createPlayer();
        this.audioPlayer.addListener(new PlayerListener(this.audioPlayer, this.musicScheduler));
        this.audioHandler = new AudioHandler(this.audioPlayer);
    }

    public void queue(String identifier, Consumer<MusicLoadResult> callback) {
        this.playerManager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicScheduler.queue(audioTrack);
                play();
                callback.accept(MusicLoadResult.SUCCESS);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                for (AudioTrack audioTrack : audioPlaylist.getTracks()) {
                    musicScheduler.queue(audioTrack);
                }
                play();
                callback.accept(MusicLoadResult.SUCCESS);
            }

            @Override
            public void noMatches() {
                callback.accept(MusicLoadResult.NO_MATCH);
            }

            @Override
            public void loadFailed(FriendlyException e) {
                callback.accept(MusicLoadResult.FAILED);
            }
        });
    }

    public boolean isPlaying() {
        return this.audioPlayer.getPlayingTrack() != null && !isPaused();
    }

    public boolean isPaused() {
        return this.audioPlayer.isPaused();
    }

    public void pause() {
        this.audioPlayer.setPaused(true);
    }

    public void resume() {
        this.audioPlayer.setPaused(false);
    }

    private void play() {
        if (!this.musicScheduler.isEmpty() && !isPlaying()) {
            AudioTrack track = this.musicScheduler.next();
            this.audioPlayer.playTrack(track);
        }
    }

    public AudioHandler getAudioHandler() {
        return audioHandler;
    }

    public static MusicController getInstance() {
        if (instance == null)
            instance = new MusicController();
        return instance;
    }
}
