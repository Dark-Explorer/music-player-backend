package com.darkexplorer.music_player.repository;

import com.darkexplorer.music_player.entity.User;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaAttributeConverter<User, String> {
}
