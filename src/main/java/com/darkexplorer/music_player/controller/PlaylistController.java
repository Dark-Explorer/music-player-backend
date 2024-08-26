package com.darkexplorer.music_player.controller;

import com.darkexplorer.music_player.dto.ApiResponse;
import com.darkexplorer.music_player.dto.request.PlaylistRequest;
import com.darkexplorer.music_player.dto.response.PlaylistResponse;
import com.darkexplorer.music_player.service.PlaylistService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistController {
    PlaylistService playlistService;

    @PostMapping
    ApiResponse<PlaylistResponse> createPlaylist(@RequestBody PlaylistRequest request) {
        return ApiResponse.<PlaylistResponse>builder()
                .result(playlistService.createPlaylist(request))
                .build();
    }

    @PutMapping("/{playlistId}")
    ApiResponse<PlaylistResponse> updatePlaylist(@PathVariable Long playlistId, @RequestBody PlaylistRequest request) {
        return ApiResponse.<PlaylistResponse>builder()
                .result(playlistService.updatePlaylist(playlistId, request))
                .build();
    }

    @DeleteMapping("/{playlistId}")
    ApiResponse<String> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ApiResponse.<String>builder()
                .result("Playlist has been deleted")
                .build();
    }

    @GetMapping
    ApiResponse<List<PlaylistResponse>> getPlaylistsByName(@RequestParam String name) {
        return ApiResponse.<List<PlaylistResponse>>builder()
                .result(playlistService.getPlaylistsByName(name))
                .build();
    }

    @GetMapping("/myPlaylists")
    ApiResponse<List<PlaylistResponse>> getMyPlaylists() {
        return ApiResponse.<List<PlaylistResponse>>builder()
                .result(playlistService.getMyPlaylists())
                .build();
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    ApiResponse<PlaylistResponse> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return ApiResponse.<PlaylistResponse>builder()
                .result(playlistService.addSongToPlaylist(playlistId, songId))
                .build();
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    ApiResponse<PlaylistResponse> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return ApiResponse.<PlaylistResponse>builder()
                .result(playlistService.removeSongFromPlaylist(playlistId, songId))
                .build();
    }
}
