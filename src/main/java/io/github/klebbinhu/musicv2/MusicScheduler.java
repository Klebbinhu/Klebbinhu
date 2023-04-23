package io.github.klebbinhu.musicv2;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayDeque;
import java.util.Queue;

public class MusicScheduler {

    private final Queue<AudioTrack> musicQueue = new ArrayDeque<>();

    public boolean hasNext() {
        return !this.musicQueue.isEmpty();
    }

    public AudioTrack next() {
        return this.musicQueue.poll();
    }

    public void queue(AudioTrack track) {
        this.musicQueue.add(track);
    }

    public Queue<AudioTrack> getQueue() {
        return this.musicQueue;
    }

    public int getPosition(AudioTrack audioTrack) {
        int index = 0;
        for (AudioTrack track : this.musicQueue) {
            if (track.equals(audioTrack))
                return index;
            index++;
        }
        return -1;
    }
}
