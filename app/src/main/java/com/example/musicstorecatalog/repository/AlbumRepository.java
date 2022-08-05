package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
