package com.darkexplorer.music_player.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    @Lob
    String image;
    @Column(length = 1024)
    @Lob
    String sound_link;

    @ManyToMany
    @JoinTable(name = "song_artist",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    Set<Artist> artists = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "songs")
    Set<Playlist> playlists = new HashSet<>();
}
