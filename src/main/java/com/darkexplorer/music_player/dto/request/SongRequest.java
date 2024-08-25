package com.darkexplorer.music_player.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongRequest {
    String title;
    String image;
    String sound_link;
    Set<Long> artistsId;
}
