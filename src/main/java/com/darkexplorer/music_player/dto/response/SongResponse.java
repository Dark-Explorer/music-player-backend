package com.darkexplorer.music_player.dto.response;

import com.darkexplorer.music_player.entity.Artist;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongResponse {
    String title;
    String image;
    String sound_link;
    Set<ArtistResponse> artists;
}
