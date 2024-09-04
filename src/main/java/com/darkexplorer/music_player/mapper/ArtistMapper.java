package com.darkexplorer.music_player.mapper;

import com.darkexplorer.music_player.dto.request.ArtistRequest;
import com.darkexplorer.music_player.dto.response.ArtistResponse;
import com.darkexplorer.music_player.entity.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    Artist toArtist(ArtistRequest request);
    Artist responseToArtist(ArtistResponse response);
    ArtistResponse toArtistResponse(Artist artist);
}
