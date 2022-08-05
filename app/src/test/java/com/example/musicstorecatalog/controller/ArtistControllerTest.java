package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Artist;
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
@WebMvcTest(ArtistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ServiceLayer service;
    Artist artistInput;
    Artist artistOutput;
    Artist artistUpdate;
    List<Artist> artistList;

    @Before
    public void setUp() throws Exception {
        artistInput = new Artist( "test artist", "test artist instagram", "test artist twitter");
        artistOutput = new Artist(1, "test artist", "test artist instagram", "test artist twitter");
        artistUpdate = new Artist(1, "test updating artist", "test artist instagram", "test artist twitter");
        artistList = new ArrayList<>();
        artistList.add(artistOutput);

        when(this.service.createArtist(artistInput)).thenReturn(artistOutput);
        when(this.service.getArtistById(1)).thenReturn(artistOutput);
        when(this.service.getAllArtist()).thenReturn(artistList);
        when(this.service.updateArtist(artistUpdate)).thenReturn(artistUpdate);
    }

    @Test
    public void shouldReturnNewArtistOnPostRequest() throws Exception{
        mockMvc.perform(post("/artist")
                        .content(mapper.writeValueAsString(artistInput))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(artistOutput)));
    }

    @Test
    public void shouldReturnArtistFindById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/artist/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldReturnAllArtistOnGetAllRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/artist")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(artistList)));
    }

    @Test
    public void shouldReturnArtistUponUpdateRequest() throws Exception{
        mockMvc.perform(put("/artist")
                        .content(mapper.writeValueAsString(artistUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(artistUpdate)));
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeleteArtist() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldThrow422ExceptionWhenWebsiteIsEmpty () throws Exception {
        Artist artistError = new Artist();
        artistError.setInstagram("error instagram");
        artistError.setTwitter("error twitter");

        mockMvc.perform(post("/artist")
                        .content(mapper.writeValueAsString(artistError))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}