package com.darkexplorer.music_player.controller;

import com.darkexplorer.music_player.dto.ApiResponse;
import com.darkexplorer.music_player.dto.request.ArtistRequest;
import com.darkexplorer.music_player.dto.response.ArtistResponse;
import com.darkexplorer.music_player.service.ArtistService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistController {
    ArtistService artistService;

    @PostMapping
    ApiResponse<ArtistResponse> createArtist(@RequestBody ArtistRequest request) {
        return ApiResponse.<ArtistResponse>builder()
                .result(artistService.createArtist(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ArtistResponse>> getAllArtists() {
        return ApiResponse.<List<ArtistResponse>>builder()
                .result(artistService.getAllArtists())
                .build();
    }

    @PutMapping("/{artistId}")
    ApiResponse<ArtistResponse> updateArtist(@PathVariable Long artistId, @RequestBody ArtistRequest request) {
        return ApiResponse.<ArtistResponse>builder()
                .result(artistService.editArtist(artistId, request))
                .build();
    }

    @DeleteMapping("/{artistId}")
    ApiResponse<String> deleteArtist(@PathVariable Long artistId) {
        artistService.deleteArtist(artistId);
        return ApiResponse.<String>builder()
                .result("Artist has been deleted")
                .build();
    }

    @GetMapping("/{artistName}")
    ApiResponse<List<ArtistResponse>> getArtistsByName(@PathVariable String artistName) {
        return ApiResponse.<List<ArtistResponse>>builder()
                .result(artistService.getArtistByName(artistName))
                .build();
    }
}
