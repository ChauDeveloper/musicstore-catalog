package com.example.musicstorecatalog.controller;

import static org.junit.Assert.*;
import com.example.musicstorecatalog.model.Track;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ServiceLayer service;
    Track trackInput;
    Track trackOutput;
    Track trackUpdate;
    List<Track> trackList;

    @Before
    public void setUp() throws Exception {
        trackInput = new Track(1, "track 1", 120);
        trackOutput = new Track(1, 1, "track 1", 120);
        trackUpdate = new Track(1, 1, "track 1 update", 120);
        trackList = new ArrayList<>();
        trackList.add(trackOutput);

        when(this.service.createTrack(trackInput)).thenReturn(trackOutput);
        when(this.service.getTrackById(1)).thenReturn(trackOutput);
        when(this.service.getAllTrack()).thenReturn(trackList);
        when(this.service.updateTrack(trackUpdate)).thenReturn(trackUpdate);
    }

    @Test
    public void shouldReturnNewTrackOnPostRequest() throws Exception{
        mockMvc.perform(post("/track")
                        .content(mapper.writeValueAsString(trackInput))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(trackOutput)));
    }

    @Test
    public void shouldReturnTrackFindById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/track/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldReturnAllTrackOnGetAllRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/track")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(trackList)));
    }

    @Test
    public void shouldReturnTrackUponUpdateRequest() throws Exception{
        mockMvc.perform(put("/track")
                        .content(mapper.writeValueAsString(trackUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(trackUpdate)));
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeleteTrack() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/track/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldThrow422ExceptionWhenTitleIsEmpty () throws Exception {
        Track trackError = new Track();
        trackError.setAlbumId(1);
        trackError.setRunTime(120);

        mockMvc.perform(post("/track")
                        .content(mapper.writeValueAsString(trackError))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}