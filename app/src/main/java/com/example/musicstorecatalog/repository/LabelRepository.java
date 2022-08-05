package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
}
