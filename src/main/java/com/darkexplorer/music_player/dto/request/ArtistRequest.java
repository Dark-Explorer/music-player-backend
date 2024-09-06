package com.darkexplorer.music_player.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArtistRequest {
    @NotBlank
    String name;
    String gender;
    int yob;
    String image;
}
