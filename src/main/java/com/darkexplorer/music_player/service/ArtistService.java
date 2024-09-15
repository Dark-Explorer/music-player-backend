package com.darkexplorer.music_player.service;

import com.darkexplorer.music_player.dto.request.ArtistRequest;
import com.darkexplorer.music_player.dto.response.ArtistResponse;
import com.darkexplorer.music_player.entity.Artist;
import com.darkexplorer.music_player.exception.AppException;
import com.darkexplorer.music_player.exception.ErrorCode;
import com.darkexplorer.music_player.mapper.ArtistMapper;
import com.darkexplorer.music_player.repository.IArtistRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistService {
    IArtistRepo artistRepo;
    ArtistMapper artistMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    public ArtistResponse createArtist(ArtistRequest request) {
        Artist artist = artistMapper.toArtist(request);
        artist = artistRepo.save(artist);
        return artistMapper.toArtistResponse(artist);
    }

    public List<ArtistResponse> getAllArtists() {
        List<Artist> artists = artistRepo.findAll();
        return artists.stream().map(artistMapper::toArtistResponse).toList();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ArtistResponse editArtist(Long id, ArtistRequest request) {
        Artist artist = artistRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ARTIST_NOT_FOUND));
        artist.setName(request.getName());
        artist.setGender(request.getGender());
        artist.setYob(request.getYob());
        artist.setImage(request.getImage());
        artist = artistRepo.save(artist);
        return artistMapper.toArtistResponse(artist);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteArtist(Long id) {
        artistRepo.deleteById(id);
    }

    public List<ArtistResponse> getArtistByName(String name) {
        List<Artist> artists = artistRepo.getArtistsByNameContaining(name);
        return artists.stream().map(artistMapper::toArtistResponse).toList();
    }

    public ArtistResponse getArtistById(Long id) {
        Artist artist = artistRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ARTIST_NOT_FOUND));
        return artistMapper.toArtistResponse(artist);
    }
}
