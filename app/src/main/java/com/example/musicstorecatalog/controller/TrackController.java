package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Track;
import com.example.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/track")
public class TrackController {
    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@RequestBody @Valid Track track){
        return service.createTrack(track);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTrack(){
        return service.getAllTrack();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrackById(@PathVariable Integer id){
        return service.getTrackById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Track updateTrack(@RequestBody @Valid Track track){
        return service.updateTrack(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable Integer id){
        service.deleteTrack(id);
    }
}
