package io.github.klebbinhu.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.music.events.*;

import java.util.Queue;
import java.util.function.Consumer;

public class MusicController {

    private final MusicScheduler scheduler = new MusicScheduler();
    private final LavaAudioPlayer audioPlayer = new LavaAudioPlayer(this.scheduler);
    private final AudioProvider audioProvider = new AudioProvider(this.audioPlayer.getPlayer());

    public void queue(String identifier, Consumer<MusicQueueEvent> callback) {
        this.audioPlayer.queue(identifier, callback);
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

    public void clearScheduler() {
        this.scheduler.clear();
    }
}
