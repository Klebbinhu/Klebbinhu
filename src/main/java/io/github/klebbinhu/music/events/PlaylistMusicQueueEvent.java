package io.github.klebbinhu.music.events;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;

public class PlaylistMusicQueueEvent extends MusicQueueEvent {

    private final AudioPlaylist playlist;

    public PlaylistMusicQueueEvent(AudioPlaylist playlist) {
        this.playlist = playlist;
    }

    public AudioPlaylist getPlaylist() {
        return this.playlist;
    }
}
