package com.darkexplorer.music_player.entity;


import com.darkexplorer.music_player.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String username;
    String password;
    String email;
    Role role;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user", orphanRemoval = true)
    Set<Playlist> playlists;
}
