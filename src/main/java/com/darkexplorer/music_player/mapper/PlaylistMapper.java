package com.darkexplorer.music_player.mapper;

import com.darkexplorer.music_player.dto.request.PlaylistRequest;
import com.darkexplorer.music_player.dto.response.PlaylistResponse;
import com.darkexplorer.music_player.entity.Playlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    Playlist toPlaylist(PlaylistRequest request);
    PlaylistResponse toPlaylistResponse(Playlist playlist);
}
