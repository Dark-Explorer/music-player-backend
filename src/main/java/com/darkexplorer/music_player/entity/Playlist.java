package com.darkexplorer.music_player.entity;

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

public class Playlist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    String id;
    String name;

    @ManyToMany(mappedBy = "playlists")
    Set<Song> songs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
