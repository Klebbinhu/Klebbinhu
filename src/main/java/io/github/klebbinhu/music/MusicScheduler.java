package io.github.klebbinhu.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;

public class MusicScheduler {

    private final List<AudioTrack> musicQueue = new ArrayList<>();

    public boolean isEmpty() {
        return this.musicQueue.isEmpty();
    }

    public void queue(AudioTrack music) {
        this.musicQueue.add(music);
    }

    public List<AudioTrack> getQueue() {
        return this.musicQueue;
    }

    public AudioTrack next() {
        return this.musicQueue.get(0);
    }

}
