package com.darkexplorer.music_player.controller;

import com.darkexplorer.music_player.dto.ApiResponse;
import com.darkexplorer.music_player.dto.request.SongRequest;
import com.darkexplorer.music_player.dto.response.SongResponse;
import com.darkexplorer.music_player.service.SongService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SongController {
    SongService songService;

    @PostMapping
    ApiResponse<SongResponse> createSong(@RequestBody SongRequest request) {
        return ApiResponse.<SongResponse>builder()
                .result(songService.createSong(request))
                .build();
    }

    @PutMapping("/{songId}")
    ApiResponse<SongResponse> updateSong(@PathVariable Long songId, @RequestBody SongRequest request) {
        return ApiResponse.<SongResponse>builder()
                .result(songService.editSong(songId, request))
                .build();
    }

    @DeleteMapping("/{songId}")
    ApiResponse<String> deleteSong(@PathVariable Long songId) {
        songService.deleteSong(songId);
        return ApiResponse.<String>builder()
                .result("Song has been deleted")
                .build();
    }

    @GetMapping("/{artistId}")
    ApiResponse<List<SongResponse>> getSongsByArtist(@PathVariable Long artistId) {
        return ApiResponse.<List<SongResponse>>builder()
                .result(songService.getAllSongsByArtist(artistId))
                .build();
    }

    @GetMapping("/search")
    ApiResponse<List<SongResponse>> searchSongs(@RequestParam String title) {
        return ApiResponse.<List<SongResponse>>builder()
                .result(songService.getSongsByTitle(title))
                .build();
    }
}
