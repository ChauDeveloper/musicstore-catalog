package com.example.musicstorecatalog.controller;
import com.example.musicstorecatalog.model.Label;
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
import org.springframework.mock.web.MockHttpServletRequest;
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
@WebMvcTest(LabelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ServiceLayer service;
    Label labelInput;
    Label labelOutput;
    Label labelUpdate;
    List<Label> labelList;

    @Before
    public void setUp() throws Exception {
        labelInput = new Label( "label test", "www.labeltest.com");
        labelOutput = new Label(1, "label test", "www.labeltest.com");
        labelUpdate = new Label(1, "label update test", "www.labeltest.com");
        labelList = new ArrayList<>();
        labelList.add(labelOutput);

        when(this.service.createLabel(labelInput)).thenReturn(labelOutput);
        when(this.service.getLabelById(1)).thenReturn(labelOutput);
        when(this.service.getAllLabel()).thenReturn(labelList);
        when(this.service.updateLabel(labelUpdate)).thenReturn(labelUpdate);
    }

    @Test
    public void shouldReturnNewLabelOnPostRequest() throws Exception{
        mockMvc.perform(post("/label")
                .content(mapper.writeValueAsString(labelInput))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(labelOutput)));
    }

    @Test
    public void shouldReturnLabelFindById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/label/{id}",1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void shouldReturnAllLabelOnGetAllRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/label")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(labelList)));
    }

    @Test
    public void shouldReturnLabelUponUpdateRequest() throws Exception{
        mockMvc.perform(put("/label")
                .content(mapper.writeValueAsString(labelUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(labelUpdate)));
    }

    @Test
    public void shouldThrow422ExceptionWhenNameIsEmpty() throws Exception {
        Label labelError = new Label();
        labelError.setWebsite("www.error.com");

        mockMvc.perform(post("/label")
                        .content(mapper.writeValueAsString(labelError))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeleteLabel() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/label/{id}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}