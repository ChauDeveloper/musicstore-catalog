package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Label;
import com.example.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/label")
public class LabelController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody @Valid Label label){
        return service.createLabel(label);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabel(){
        return service.getAllLabel();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable Integer id){
        return service.getLabelById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Label updateLabel(@RequestBody @Valid Label label){
        return service.updateLabel(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Integer id){
        service.deleteLabel(id);
    }
}
