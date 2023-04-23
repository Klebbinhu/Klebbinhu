package io.github.klebbinhu.musicv2.events;

import io.github.klebbinhu.musicv2.MusicQueueResult;

public class FailedMusicQueueEvent extends MusicQueueEvent {

    public FailedMusicQueueEvent() {
        super(MusicQueueResult.FAILED);
    }
}
