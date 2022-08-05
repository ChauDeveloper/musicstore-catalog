package com.example.musicstorecatalog.service;

import com.example.musicstorecatalog.model.Album;
import com.example.musicstorecatalog.model.Artist;
import com.example.musicstorecatalog.model.Label;
import com.example.musicstorecatalog.model.Track;
import com.example.musicstorecatalog.repository.AlbumRepository;
import com.example.musicstorecatalog.repository.ArtistRepository;
import com.example.musicstorecatalog.repository.LabelRepository;
import com.example.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ServiceLayer {
    AlbumRepository albumRepository;
    ArtistRepository artistRepository;
    LabelRepository labelRepository;
    TrackRepository trackRepository;

    @Autowired
    public ServiceLayer(AlbumRepository albumRepository, ArtistRepository artistRepository, LabelRepository labelRepository,TrackRepository trackRepository){
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.labelRepository = labelRepository;
        this.trackRepository = trackRepository;
    }

    //Label service layer
    public Label createLabel(Label label){
        if (label == null) throw new IllegalArgumentException("No label information filled in!");

        label = labelRepository.save(label);
        return label;
    }
    public List<Label> getAllLabel(){
        List<Label> labelList = labelRepository.findAll();
        if (labelList == null) {
            return null; }
        return labelList;
    }
    public Label getLabelById(Integer id){
        Optional<Label> label = labelRepository.findById(id);
        if (label == null){
            return null;
        }
        return label.get();
    }
    public Label updateLabel(Label label){
        if(label == null) throw new IllegalArgumentException("No label information provided!");
        if(label.getId()==null) throw new NoSuchElementException("Can't find a label by this ID");
        label = labelRepository.save(label);
        return label;
    }
    public void deleteLabel(Integer id){
        if (labelRepository.findById(id) == null) throw new NoSuchElementException("No label found with this ID");
        labelRepository.deleteById(id);
    }


    //Artist service layer
    public Artist createArtist(Artist artist){
        if (artist == null) throw new IllegalArgumentException("No information was filled for this artist");
       artist = artistRepository.save(artist);
        return artist;
    }
    public List<Artist> getAllArtist(){
        List<Artist> artistList = artistRepository.findAll();
        return artistList;
    }
    public Artist getArtistById(Integer id){
        Optional<Artist> artist = artistRepository.findById(id);
        if (artistRepository.findById(id) == null) throw new NoSuchElementException("No Artist found at this ID");
        return artist.get();
    }
    public Artist updateArtist(Artist artist){
        if (artistRepository.findById(artist.getId()) == null) throw new NoSuchElementException("No Artist found at this ID");
        artist = artistRepository.save(artist);
        return artist;
    }
    public void deleteArtist(Integer id){
        if (artistRepository.findById(id) == null) throw new NoSuchElementException("No Artist found at this ID");
        artistRepository.deleteById(id);
    }


    //Album service layer
    public Album createAlbum(Album album){
        if (album == null) throw new NullPointerException("No information is filled for Album");
        album = albumRepository.save(album);
        return album;
    }
    public List<Album> getAllAlbum(){
        return albumRepository.findAll();
    }
    public Album getAlbumById(Integer id){
        Optional<Album> album = albumRepository.findById(id);
        if (album == null) throw new NoSuchElementException("No Album found with this ID");
        return album.get();
    }
    public Album updateAlbum(Album album){
        if (albumRepository.findById(album.getId()) == null) throw new NoSuchElementException("Can't find this Album at this ID");
        album = albumRepository.save(album);
        return album;
    }
    public void deleteAlbum(Integer id){
        if (albumRepository.findById(id) == null) throw new NoSuchElementException("Can't find this Album at this ID");
        albumRepository.deleteById(id);
    }

    //Track service layer
    public Track createTrack(Track track){
        if (track == null) throw new NullPointerException("No information is filled for track");
        track = trackRepository.save(track);
        return track;
    }
    public List<Track> getAllTrack(){
        List<Track> trackList = trackRepository.findAll();
        return trackList;
    }
    public Track getTrackById(Integer id){
       Optional<Track> track = trackRepository.findById(id);
        if (track == null) {return null;}
        return track.get();
    }
    public Track updateTrack(Track track){
        if (trackRepository.findById(track.getId()) == null ) throw new NoSuchElementException("can't find Track with this ID");
        track = trackRepository.save(track);
        return track;
    }
    public void deleteTrack(Integer id){
        if (trackRepository.findById(id) == null ) throw new NoSuchElementException("can't find Track with this ID");
        trackRepository.deleteById(id);
    }
}
