package com.darkexplorer.music_player.mapper;

import com.darkexplorer.music_player.dto.request.SongRequest;
import com.darkexplorer.music_player.dto.response.SongResponse;
import com.darkexplorer.music_player.entity.Artist;
import com.darkexplorer.music_player.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SongMapper {
    @Mapping(target = "artists", ignore = true)
    Song toSong(SongRequest request);
    SongResponse toSongResponse(Song song);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "artists", source = "artistsId")
//    @Mapping(target = "playlists", ignore = true)
//    Song toSong(SongRequest songRequest);

//    @Named("artistsToArtistsId")
//    default Set<Long> artistsToArtistsId(Set<Artist> artists) {
//        return artists.stream()
//                .map(Artist::getId)
//                .collect(java.util.stream.Collectors.toSet());
//    }
}
