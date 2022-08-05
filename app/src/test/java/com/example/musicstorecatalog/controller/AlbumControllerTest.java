package com.example.musicstorecatalog.controller;

import static org.junit.Assert.*;

import com.example.musicstorecatalog.model.Album;
import com.example.musicstorecatalog.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ServiceLayer service;
    Album albumInput;
    Album albumOutput;
    Album albumUpdate;
    List<Album> albumList;

    @Before
    public void setUp() throws Exception {
        albumInput = new Album("test title", 1, LocalDate.of(2022, 8, 02), 1, new BigDecimal("12.99"));
        albumOutput = new Album(1, "test title", 1, LocalDate.of(2022, 8, 02), 1, new BigDecimal("12.99"));
        albumUpdate = new Album(1, "test updating title", 1, LocalDate.of(2022, 8, 02), 1, new BigDecimal("12.99"));
        albumList = new ArrayList<>();
        albumList.add(albumOutput);

        when(this.service.createAlbum(albumInput)).thenReturn(albumOutput);
        when(this.service.getAlbumById(1)).thenReturn(albumOutput);
        when(this.service.getAllAlbum()).thenReturn(albumList);
        when(this.service.updateAlbum(albumUpdate)).thenReturn(albumUpdate);
    }

    @Test
    public void shouldReturnNewAlbumOnPostRequest() throws Exception{
        mockMvc.perform(post("/album")
                        .content(mapper.writeValueAsString(albumInput))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(albumOutput)));
    }

    @Test
    public void shouldReturnAlbumFindById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/album/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldReturnAllAlbumOnGetAllRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/album")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(albumList)));
    }

    @Test
    public void shouldReturnAlbumUponUpdateRequest() throws Exception{
        mockMvc.perform(put("/album")
                        .content(mapper.writeValueAsString(albumUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(albumUpdate)));
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeleteAlbum() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/album/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldThrow422ExceptionWhenTitleIsEmpty () throws Exception {
        Album albumError = new Album();
        albumError.setArtistId(1);
        albumError.setLabelId(1);
        albumError.setReleaseDate(LocalDate.of(2022,04,02));
        albumError.setListPrice(new BigDecimal("12.99"));

        mockMvc.perform(post("/album")
                        .content(mapper.writeValueAsString(albumError))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldThrow422ExceptionWhenArtistIdIsEmpty () throws Exception {
        Album albumError = new Album();
        albumError.setTitle("error tittle");
        albumError.setLabelId(1);
        albumError.setReleaseDate(LocalDate.of(2022,04,02));
        albumError.setListPrice(new BigDecimal("12.99"));

        mockMvc.perform(post("/album")
                        .content(mapper.writeValueAsString(albumError))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldThrow422ExceptionWhenLabelIdIsEmpty () throws Exception {
        Album albumError = new Album();
        albumError.setTitle("error tittle");
        albumError.setArtistId(1);
        albumError.setReleaseDate(LocalDate.of(2022,04,02));
        albumError.setListPrice(new BigDecimal("12.99"));

        mockMvc.perform(post("/album")
                        .content(mapper.writeValueAsString(albumError))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


}