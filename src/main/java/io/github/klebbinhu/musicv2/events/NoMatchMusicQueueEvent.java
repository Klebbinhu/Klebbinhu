package io.github.klebbinhu.musicv2.events;

import io.github.klebbinhu.musicv2.MusicQueueResult;

public class NoMatchMusicQueueEvent extends MusicQueueEvent {

    public NoMatchMusicQueueEvent() {
        super(MusicQueueResult.NO_MATCH);
    }
}
