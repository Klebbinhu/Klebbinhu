package io.github.klebbinhu.musicv2.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.klebbinhu.musicv2.MusicQueueResult;

public class SingleMusicQueueEvent extends MusicQueueEvent {

    private final AudioTrack audioTrack;

    public SingleMusicQueueEvent(AudioTrack audioTrack) {
        super(MusicQueueResult.SUCCESS);
        this.audioTrack = audioTrack;
    }

    public AudioTrack getAudioTrack() {
        return audioTrack;
    }
}
