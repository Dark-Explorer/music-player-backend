package com.darkexplorer.music_player.repository;

import com.darkexplorer.music_player.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, String> {
}
