package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Artist;
import com.example.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody @Valid Artist artist){
        return service.createArtist(artist);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtist(){
        return service.getAllArtist();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable Integer id){
        return service.getArtistById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Artist updateArtist(@RequestBody @Valid Artist artist){
        return service.updateArtist(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id){
        service.deleteArtist(id);
    }
}
