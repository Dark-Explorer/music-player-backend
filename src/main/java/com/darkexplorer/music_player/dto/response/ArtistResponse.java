package com.darkexplorer.music_player.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArtistResponse {
    Long id;
    String name;
    String gender;
    int yob;
    String image;
}
