package com.example.musicstorecatalog.service;

import com.example.musicstorecatalog.model.Album;
import com.example.musicstorecatalog.model.Artist;
import com.example.musicstorecatalog.model.Label;
import com.example.musicstorecatalog.model.Track;
import com.example.musicstorecatalog.repository.AlbumRepository;
import com.example.musicstorecatalog.repository.ArtistRepository;
import com.example.musicstorecatalog.repository.LabelRepository;
import com.example.musicstorecatalog.repository.TrackRepository;
import org.hibernate.type.LocalDateType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class ServiceLayerTest {
    ServiceLayer service;
    AlbumRepository albumRepository;
    ArtistRepository artistRepository;
    LabelRepository labelRepository;
    TrackRepository trackRepository;
    Album albumReturn;
    Artist artistReturn;
    Label labelReturn;
    Track trackReturn;
    Album albumInput;
    Artist artistInput;
    Label labelInput;
    Track trackInput;
    Album albumUpdate;
    Artist artistUpdate;
    Label labelUpdate;
    Track trackUpdate;
    List<Album> albumReturnList;
    List<Artist> artistReturnList;
    List<Label> labelReturnList;
    List<Track> trackReturnList;


    @Before
    public void setUp() throws Exception {
        setUpAlbumRepositoryMock();
        setUpArtistRepositoryMock();
        setUpLabelRepositoryMock();
        setUpTrackRepositoryMock();

        service = new ServiceLayer(albumRepository, artistRepository, labelRepository, trackRepository);

    }

    private void setUpAlbumRepositoryMock() {
        albumRepository = mock(AlbumRepository.class);
        albumInput= new Album("test title", 1, LocalDate.of(2022, 8, 02), 1, new BigDecimal("12.99"));
        albumReturn = new Album(1, "test title", 1, LocalDate.of(2022, 8, 02), 1, new BigDecimal("12.99"));
        albumUpdate = new Album(1, "test update title", 1, LocalDate.of(2022, 8, 02), 1, new BigDecimal("12.99"));
        albumReturnList = new ArrayList<>();
        albumReturnList.add(albumReturn);


        doReturn(albumReturn).when(albumRepository).save(albumInput);
        doReturn(Optional.of(albumReturn)).when(albumRepository).findById(1);
        doReturn(albumReturnList).when(albumRepository).findAll();
        doReturn(albumUpdate).when(albumRepository).save(albumUpdate);
    }

    private void setUpArtistRepositoryMock() {
        artistRepository = mock(ArtistRepository.class);
        artistReturn = new Artist(1, "test artist", "test artist instagram", "test artist twitter");
        artistInput = new Artist("test artist", "test artist instagram", "test artist twitter");
        artistUpdate = new Artist(1, "test update artist", "test artist instagram", "test artist twitter");
        artistReturnList = new ArrayList<>();
        artistReturnList.add(artistReturn);

        doReturn(artistReturn).when(artistRepository).save(artistInput);
        doReturn(Optional.of(artistReturn)).when(artistRepository).findById(1);
        doReturn(artistReturnList).when(artistRepository).findAll();
        doReturn(artistUpdate).when(artistRepository).save(artistUpdate);
    }

    private void setUpLabelRepositoryMock() {
        labelRepository = mock(LabelRepository.class);
        labelReturn = new Label(1, "label test", "www.labeltest.com");
        labelInput = new Label("label test", "www.labeltest.com");
        labelUpdate = new Label(1, "label update test", "www.labeltest.com");
        labelReturnList = new ArrayList<>();
        labelReturnList.add(labelReturn);

        doReturn(labelReturn).when(labelRepository).save(labelInput);
        doReturn(Optional.of(labelReturn)).when(labelRepository).findById(1);
        doReturn(labelReturnList).when(labelRepository).findAll();
        doReturn(labelUpdate).when(labelRepository).save(labelUpdate);
    }

    private void setUpTrackRepositoryMock(){
        trackRepository = mock(TrackRepository.class);
        trackReturn = new Track(1, 1, "track 1", 120);
        trackInput = new Track(1, "track 1", 120);
        trackUpdate = new Track(1, 1, "track 1 update", 120);
        trackReturnList = new ArrayList<>();
        trackReturnList.add(trackReturn);

        doReturn(trackReturn).when(trackRepository).save(trackInput);
        doReturn(Optional.of(trackReturn)).when(trackRepository).findById(1);
        doReturn(trackReturnList).when(trackRepository).findAll();
        doReturn(trackUpdate).when(trackRepository).save(trackUpdate);
    }


    @Test
    public void findAllItems(){
        List<Album> albumList = service.getAllAlbum();
        List<Artist> artistList = service.getAllArtist();
        List<Label> labelList = service.getAllLabel();
        List<Track> trackList = service.getAllTrack();

        assertEquals(1, albumList.size());
        assertEquals(1, artistList.size());
        assertEquals(1, labelList.size());
        assertEquals(1, trackList.size());
    }

    @Test
    public void findItemById(){
        assertEquals(labelReturn, service.getLabelById(1));
        assertEquals(trackReturn, service.getTrackById(1));
        assertEquals(albumReturn, service.getAlbumById(1));
        assertEquals(artistReturn, service.getArtistById(1));
    }

    @Test
    public void createItem(){
        assertEquals(labelReturn,service.createLabel(labelInput));
        assertEquals(albumReturn,service.createAlbum(albumInput));
        assertEquals(artistReturn,service.createArtist(artistInput));
        assertEquals(trackReturn,service.createTrack(trackInput));
    }

    @Test
    public void updateItem(){
        assertEquals(albumUpdate,service.updateAlbum(albumUpdate));
        assertEquals(artistUpdate,service.updateArtist(artistUpdate));
        assertEquals(labelUpdate,service.updateLabel(labelUpdate));
        assertEquals(trackUpdate,service.updateTrack(trackUpdate));
    }
}