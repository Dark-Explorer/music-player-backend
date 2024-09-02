package com.darkexplorer.music_player.service;

import com.darkexplorer.music_player.dto.request.SongRequest;
import com.darkexplorer.music_player.dto.response.SongResponse;
import com.darkexplorer.music_player.entity.Artist;
import com.darkexplorer.music_player.entity.Song;
import com.darkexplorer.music_player.exception.AppException;
import com.darkexplorer.music_player.exception.ErrorCode;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SongService {
    ISongRepo songRepo;
    IArtistRepo artistRepo;
    SongMapper songMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SongResponse> getAllSongs() {
        List<Song> songs = songRepo.findAll();
        return songs.stream().map(songMapper::toSongResponse).toList();
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
        var artists = artistRepo.findAllById(request.getArtistsId());
        song.setArtists(new HashSet<>(artists));

        song = songRepo.save(song);
        return songMapper.toSongResponse(song);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SongResponse editSong(Long id, SongRequest request) {
        Song song = songRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));

        song.setTitle(request.getTitle());
        song.setImage(request.getImage());
        song.setSound_link(request.getSound_link());

        var artists = artistRepo.findAllById(request.getArtistsId());
        song.setArtists(new HashSet<>(artists));

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
