package com.dl.dropL.controller;

import com.dl.dropL.model.Drop;
import com.dl.dropL.service.DropService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class DropControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DropService dropService;

    @Autowired
    private ObjectMapper objectMapper;

    private Drop drop;


    @DisplayName("findAllDropsTest")
    @Test
    public void givenListOfDrops_whenGetAllDrops_thenReturnDropList() throws Exception {
        Date date = new Date();

        //given
        List<Drop> listOfDrops = new ArrayList<>();

        listOfDrops.add(Drop.builder().dropId(1L)
                .dropTitle("20 Padlocks #300")
                .demandDesc("Items to be pick up from Tony's Hardware")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build());

        listOfDrops.add(Drop.builder().dropId(1L)
                .dropTitle("10 2")
                .demandDesc("Items to be pick up from Tony's Hardware")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build());

        given(dropService.findAll()).willReturn(listOfDrops);
        //when
        ResultActions response = mockMvc.perform(get("/api/drops"));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfDrops.size())));

    }
    @DisplayName("getDropById")
    @Test
    public void givenDropId_whenGetDropById_thenReturnDropObject() throws Exception {

        Date date = new Date();

        //given
        Long dropId = 1L;
        Drop drop = Drop.builder()
                .dropTitle("20 Padlocks #300")
                .demandDesc("Items to be pick up from Tony's Hardware")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build();
        given(dropService.getDropById(dropId)).willReturn(Optional.of(drop));

        //when
        ResultActions response = mockMvc.perform(get("/api/drops/{id}", dropId));

        //then
        response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.dropTitle", is(drop.getDropTitle())))
        .andExpect(jsonPath( "$.demandDesc", is(drop.getDemandDesc())));

    }

    @DisplayName("updateDropTest")
    @Test
    public void givenUpdatedDrop_whenUpdateDrop_returnUpdatedDropObject() throws Exception {

        Date date = new Date();

        //given
        Long dropId = 1L;
        Drop savedDrop = Drop.builder()
                .dropTitle("20 Padlocks #300")
                .demandDesc("Items to be pick up from Tony's Hardware")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build();

        Drop updatedDrop = Drop.builder()
                .dropTitle("2 brooms")
                .demandDesc("Brooms must be for industrial use.")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build();
        given(dropService.getDropById(dropId)).willReturn(Optional.of(savedDrop));
        given(dropService.updateDrop(any(Drop.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/drops/{id}", dropId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDrop)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.dropTitle", is(updatedDrop.getDropTitle())))
                .andExpect(jsonPath("$.demandDesc", is(updatedDrop.getDemandDesc())));

    }


    // negative scenario
    @DisplayName("updateDropTestNeg")
    @Test
    public  void givenUpdatedDrop_whenUpdatedDrop_thenReturn404() throws Exception {
        Date date = new Date();

        //given
        Long dropId = 1L;
        Drop savedDrop = Drop.builder()
                .dropTitle("20 Padlocks #300")
                .demandDesc("Items to be pick up from Tony's Hardware")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build();

        Drop updatedDrop = Drop.builder()
                .dropTitle("2 brooms")
                .demandDesc("Brooms must be for industrial use.")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build();
        given(dropService.getDropById(dropId)).willReturn(Optional.empty());
        given(dropService.updateDrop(any(Drop.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/drops/{id}", dropId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDrop)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("deleteDropTest")
    @Test
    public void givenDropId_whenDeleteDrop_thenReturn200() throws Exception {

        //given
        long dropId = 1L;
        willDoNothing().given(dropService).deleteDrop(dropId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/drops/{id}", dropId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }


    // NOT passing at this time/ work in progress.****************************************
    @DisplayName("createDropTest")
    @Test
    public void givenDropObject_whenCreateDrop_thenReturnSavedDrop() throws Exception {

        //given
        Date date = new Date();

        Drop drop = Drop.builder()
                .dropId(1L)
                .dropTitle("20 Padlocks #300")
                .demandDesc("Items to be pick up from Tony's Hardware")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(62)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(new Date(date.getTime())).build();
        given(dropService.saveDrop(any(Drop.class)))
                .willAnswer((invocation ) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/drops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drop)));

        //then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.dropTitle",
                        is(drop.getDropTitle())))
                .andExpect(jsonPath("$.demandDesc",
                        is(drop.getDemandDesc())))
                .andExpect(jsonPath("$.latdrop",
                        is(drop.getLatdrop())))
                .andExpect(jsonPath("$.longdrop",
                        is(drop.getLongdrop())))
                .andExpect(jsonPath("$.latpickup",
                        is(drop.getLatpickup())))
                .andExpect(jsonPath("$.longpickup",
                        is(drop.getLongpickup())))
                .andExpect(jsonPath("$.payOffer",
                        is(drop.getPayOffer())))
                .andExpect(jsonPath("$.dropByDate",
                        is(drop.getDropByDate())))
                .andExpect(jsonPath("$.datePosted",
                        is(drop.getDatePosted())))
                .andExpect(jsonPath("$.lastModified",
                        is(drop.getLastModified())));

    }

}

