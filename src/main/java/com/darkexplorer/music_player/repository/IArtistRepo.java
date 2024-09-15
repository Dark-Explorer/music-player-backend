package com.darkexplorer.music_player.repository;

import com.darkexplorer.music_player.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArtistRepo extends JpaRepository<Artist, Long> {
    List<Artist> getArtistsByNameContainingIgnoreCase(String name);

}
