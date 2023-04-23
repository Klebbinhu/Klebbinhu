package io.github.klebbinhu.musicv2.events;

import io.github.klebbinhu.musicv2.MusicQueueResult;

public abstract class MusicQueueEvent {

    private final MusicQueueResult result;

    public MusicQueueEvent(MusicQueueResult result) {
        this.result = result;
    }

    public MusicQueueResult getResult() {
        return result;
    }
}
