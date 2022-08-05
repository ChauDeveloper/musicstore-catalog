package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Album;
import com.example.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/album")
public class AlbumController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody @Valid Album album){
        return service.createAlbum(album);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbum(){
        return service.getAllAlbum();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable Integer id){
        return service.getAlbumById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Album updateAlbum(@RequestBody @Valid Album album){
        return service.updateAlbum(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id){
        service.deleteAlbum(id);
    }
}
