package io.github.klebbinhu.music.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class SingleMusicQueueEvent extends MusicQueueEvent {

    private final AudioTrack audioTrack;
    private final boolean startedPlaying;

    public SingleMusicQueueEvent(AudioTrack audioTrack, boolean startedPlaying) {
        this.audioTrack = audioTrack;
        this.startedPlaying = startedPlaying;
    }

    public AudioTrack getAudioTrack() {
        return this.audioTrack;
    }

    public boolean startedPlaying() {
        return this.startedPlaying;
    }
}
