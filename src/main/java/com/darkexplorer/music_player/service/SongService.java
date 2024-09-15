package com.darkexplorer.music_player.service;

import com.darkexplorer.music_player.dto.request.ArtistRequest;
import com.darkexplorer.music_player.dto.request.SongRequest;
import com.darkexplorer.music_player.dto.response.ArtistResponse;
import com.darkexplorer.music_player.dto.response.SongResponse;
import com.darkexplorer.music_player.entity.Artist;
import com.darkexplorer.music_player.entity.Song;
import com.darkexplorer.music_player.exception.AppException;
import com.darkexplorer.music_player.exception.ErrorCode;
import com.darkexplorer.music_player.mapper.ArtistMapper;
import com.darkexplorer.music_player.mapper.SongMapper;
import com.darkexplorer.music_player.repository.IArtistRepo;
import com.darkexplorer.music_player.repository.ISongRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SongService {
    ISongRepo songRepo;
    IArtistRepo artistRepo;
    SongMapper songMapper;
    ArtistMapper artistMapper;

    public List<SongResponse> getAllSongs() {
        List<Song> songs = songRepo.findAll();
        return songs.stream().map(songMapper::toSongResponse).toList();
    }

    public SongResponse getSongById(Long id) {
        Song song = songRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));
        return songMapper.toSongResponse(song);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SongResponse createSong(SongRequest request) {
        Song song = songMapper.toSong(request);

//        Song song = new Song();
//        song.setTitle(request.getTitle());
//        song.setImage(request.getImage());
//        song.setSound_link(request.getSound_link());

//        for (Long artistId : request.getArtistsId()) {
//            Artist artist = artistRepo.findById(artistId)
//                    .orElseThrow(() -> new AppException(ErrorCode.ARTIST_NOT_FOUND));
//            song.getArtists().add(artist);
//        }
        Set<Artist> artists = new HashSet<>();
        for (ArtistResponse artist : request.getArtists()) {
            artists.add(artistMapper.responseToArtist(artist));
            System.out.println(artist);
        }
        song.setArtists(artists);
        try {
            song = songRepo.save(song);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return songMapper.toSongResponse(song);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SongResponse editSong(Long id, SongRequest request) {
        Song song = songRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));

        song.setTitle(request.getTitle());
        song.setImage(request.getImage());
        song.setSound_link(request.getSound_link());
        song.setArtists(request.getArtists().stream().map(artistMapper::responseToArtist).collect(Collectors.toSet()));

        song = songRepo.save(song);
        return songMapper.toSongResponse(song);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSong(Long id) {
        songRepo.deleteById(id);
    }

    public List<SongResponse> getAllSongsByArtist(Long artistId) {
        Artist artist = artistRepo.findById(artistId).orElseThrow(() -> new AppException(ErrorCode.ARTIST_NOT_FOUND));
        List<Song> songs = artist.getSongs().stream().toList();
        return songs.stream().map(songMapper::toSongResponse).toList();
    }

    public List<SongResponse> getSongsByTitle(String title) {
        List<Song> songs = songRepo.findSongsByTitleContainingIgnoreCase(title);
        return songs.stream().map(songMapper::toSongResponse).toList();
    }
}
