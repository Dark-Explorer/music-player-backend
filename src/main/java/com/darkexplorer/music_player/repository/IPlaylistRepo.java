package com.darkexplorer.music_player.repository;

import com.darkexplorer.music_player.entity.Playlist;
import com.darkexplorer.music_player.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlaylistRepo extends JpaRepository<Playlist, Long> {
    List<Playlist> findPlaylistByNameContainingIgnoreCase(String name);
    List<Playlist> findPlaylistByUser(User user);
}
