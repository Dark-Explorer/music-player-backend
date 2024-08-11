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

public class Song {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    String id;
    String title;
    String sound;

    @ManyToMany(cascade = CascadeType.MERGE)
    Set<Artist> artists;

    @ManyToMany(cascade = CascadeType.MERGE)
    Set<Playlist> playlists;
}
