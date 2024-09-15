package com.darkexplorer.music_player.controller;

import com.darkexplorer.music_player.dto.response.ArtistResponse;
import com.darkexplorer.music_player.dto.response.SongResponse;
import com.darkexplorer.music_player.service.ArtistService;
import com.darkexplorer.music_player.service.SongService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {
    SongService songService;
    ArtistService artistService;

    @GetMapping
    public List<Object> getSearchResult(@RequestParam String keyword) {
        List<ArtistResponse> artists = artistService.getArtistByName(keyword);
        List<SongResponse> songs = songService.getSongsByTitle(keyword);
        List<Object> results = new ArrayList<>(artists);
        results.addAll(songs);
        return results;
    }

}
