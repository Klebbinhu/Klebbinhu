package io.github.klebbinhu.musicv2;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import io.github.klebbinhu.musicv2.events.MusicQueueEvent;

public class PlaylistMusicQueueEvent extends MusicQueueEvent {

    private final AudioPlaylist playlist;

    public PlaylistMusicQueueEvent(AudioPlaylist playlist) {
        super(MusicQueueResult.SUCCESS);
        this.playlist = playlist;
    }

    public AudioPlaylist getPlaylist() {
        return playlist;
    }
}
