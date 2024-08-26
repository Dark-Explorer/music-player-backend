package com.darkexplorer.music_player.service;

import com.darkexplorer.music_player.dto.request.PlaylistRequest;
import com.darkexplorer.music_player.dto.response.PlaylistResponse;
import com.darkexplorer.music_player.entity.Playlist;
import com.darkexplorer.music_player.entity.Song;
import com.darkexplorer.music_player.entity.User;
import com.darkexplorer.music_player.exception.AppException;
import com.darkexplorer.music_player.exception.ErrorCode;
import com.darkexplorer.music_player.mapper.PlaylistMapper;
import com.darkexplorer.music_player.repository.IPlaylistRepo;
import com.darkexplorer.music_player.repository.ISongRepo;
import com.darkexplorer.music_player.repository.IUserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistService {
    IPlaylistRepo playlistRepo;
    PlaylistMapper playlistMapper;
    IUserRepo userRepo;
    ISongRepo songRepo;

    public PlaylistResponse createPlaylist(PlaylistRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Playlist playlist = playlistMapper.toPlaylist(request);
        playlist.setUser(user);
        playlist = playlistRepo.save(playlist);
        return playlistMapper.toPlaylistResponse(playlist);
    }

    public PlaylistResponse updatePlaylist(Long id, PlaylistRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Playlist playlist = playlistRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        if (Objects.equals(user.getId(), playlist.getUser().getId())) {
            playlist.setName(request.getName());
            playlist = playlistRepo.save(playlist);
        } else throw new AppException(ErrorCode.UNAUTHORIZED);
        return playlistMapper.toPlaylistResponse(playlist);
    }

    public void deletePlaylist(Long id) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Playlist playlist = playlistRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));
        if (Objects.equals(user.getId(), playlist.getUser().getId())) {
            playlistRepo.deleteById(id);
        } else throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    public List<PlaylistResponse> getPlaylistsByName(String name) {
        List<Playlist> playlists = playlistRepo.findPlaylistByNameContainingIgnoreCase(name);
        return playlists.stream().map(playlistMapper::toPlaylistResponse).toList();
    }

    public List<PlaylistResponse> getMyPlaylists() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Playlist> playlists = playlistRepo.findPlaylistByUser(user);
        return playlists.stream().map(playlistMapper::toPlaylistResponse).toList();
    }

    public PlaylistResponse addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId).orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        if (!Objects.equals(playlist.getUser().getUsername(), name)) throw new AppException(ErrorCode.UNAUTHORIZED);
        Song song = songRepo.findById(songId).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));

        playlist.getSongs().add(song);
        playlistRepo.save(playlist);
        return playlistMapper.toPlaylistResponse(playlist);
    }

    public PlaylistResponse removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId).orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        if (!Objects.equals(playlist.getUser().getUsername(), name)) throw new AppException(ErrorCode.UNAUTHORIZED);
        for (Song song : playlist.getSongs()) {
            if (song.getId().equals(songId)) {
                playlist.getSongs().remove(song);
                break;
            }
        }
        // Neu xoa bai hat khong co trong playlist?
        playlistRepo.save(playlist);
        return playlistMapper.toPlaylistResponse(playlist);
    }
}
