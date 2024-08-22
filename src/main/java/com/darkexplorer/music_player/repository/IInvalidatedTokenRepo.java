package com.darkexplorer.music_player.repository;

import com.darkexplorer.music_player.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvalidatedTokenRepo extends JpaRepository<InvalidatedToken, String> {
}
