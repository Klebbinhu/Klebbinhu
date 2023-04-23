package io.github.klebbinhu.musicv2;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.musicv2.events.FailedMusicQueueEvent;
import io.github.klebbinhu.musicv2.events.MusicQueueEvent;
import io.github.klebbinhu.musicv2.events.NoMatchMusicQueueEvent;
import io.github.klebbinhu.musicv2.events.SingleMusicQueueEvent;

import java.util.Queue;
import java.util.function.Consumer;

public class MusicController {

    private final MusicScheduler scheduler = new MusicScheduler();
    private final LavaAudioPlayer audioPlayer = new LavaAudioPlayer(this.scheduler);
    private final AudioProvider audioProvider = new AudioProvider(this.audioPlayer.getPlayer());

    public void queue(String identifier, Consumer<MusicQueueEvent> callback) {
        this.audioPlayer.getPlayerManager().loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                scheduler.queue(audioTrack);
                callback.accept(new SingleMusicQueueEvent(audioTrack));
                if (!audioPlayer.isPlaying())
                    audioPlayer.playNext();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                for (AudioTrack track : audioPlaylist.getTracks()) {
                    scheduler.queue(track);
                }
                callback.accept(new PlaylistMusicQueueEvent(audioPlaylist));
                if (!audioPlayer.isPlaying())
                    audioPlayer.playNext();
            }

            @Override
            public void noMatches() {
                callback.accept(new NoMatchMusicQueueEvent());
            }

            @Override
            public void loadFailed(FriendlyException e) {
                callback.accept(new FailedMusicQueueEvent());
            }
        });
    }

    public AudioProvider getAudioProvider() {
        return audioProvider;
    }

    public Queue<AudioTrack> getMusicQueue() {
        return this.scheduler.getQueue();
    }

    public int getPositionInQueue(AudioTrack audioTrack) {
        return this.scheduler.getPosition(audioTrack);
    }

    public boolean isPlaying() {
        return this.audioPlayer.isPlaying();
    }

    public AudioTrack getPlaying() {
        return this.audioPlayer.getPlaying();
    }

    public void pause() {
        this.audioPlayer.pause();
    }

    public void resume() {
        this.audioPlayer.resume();
    }

    public boolean isPaused() {
        return this.audioPlayer.isPaused();
    }

    public void skip() {
        if (this.scheduler.hasNext())
            audioPlayer.playNext();
        else
            stop();
    }

    public void stop() {
        audioPlayer.stop();
    }
}
