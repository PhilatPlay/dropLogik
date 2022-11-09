package com.dl.dropL.service;

import com.dl.dropL.model.Drop;
import com.dl.dropL.repository.DropRepository;
import com.dl.dropL.service.impl.DropServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DropServiceTests {

    @Mock
    private DropRepository dropRepository;

    @InjectMocks
    private DropServiceImpl dropService;


    private Drop drop;
    private Drop drop2;


    @BeforeEach
    public void setup() {
        Date date = new Date();

        drop = Drop.builder()
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
                .lastModified(null).build();

    }

    // unit tests for saveDrop method
    @DisplayName("saveDropTest")
    @Test
    public void givenDropObject_whenSaveDrop_thenReturnDropObject(){
        //given
        given(dropRepository.save(drop)).willReturn(drop);
        //when
        Drop savedDrop = dropService.saveDrop(drop);
        //then
        assertThat(savedDrop).isNotNull();

    }

    // unit tests for saveDrop method
    @DisplayName("findAllTest")
    @Test
    public void givenDropList_whenGetAllDrops_thenReturnDropList(){
        Date date = new Date();

        drop2 = Drop.builder()
                .dropId(2L)
                .dropTitle("13 Calfs")
                .demandDesc("Items to be pick up from Tony's Farm")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(622)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(null).build();
        //given
        given(dropRepository.findAll()).willReturn(List.of(drop, drop2));
        //when
        List<Drop> dropList = dropService.findAll();
        //then
        assertThat(dropList).isNotNull();
        assertThat(dropList.size()).isEqualTo(2);
    }

    // unit tests for saveDrop method negative scenario
    @DisplayName("findAllTest")
    @Test
    public void givenEmptyDropList_whenGetAllDrops_thenReturnEmptyDropList(){
        Date date = new Date();

        drop2 = Drop.builder()
                .dropId(2L)
                .dropTitle("13 Calfs")
                .demandDesc("Items to be pick up from Tony's Farm")
                .latdrop(28.197769321617393)
                .longdrop(-81.99712547287612)
                .latpickup(29.197769321617393)
                .longpickup(-8.99712547287612)
                .payOffer(622)
                .dropByDate(new Date(date.getTime() + 24 * 60 * 60 * 1000))
                .datePosted(new Date(date.getTime()))
                .lastModified(null).build();
        //given
        given(dropRepository.findAll()).willReturn(Collections.emptyList());
        //when
        List<Drop> dropList = dropService.findAll();
        //then
        assertThat(dropList).isEmpty();
        assertThat(dropList.size()).isEqualTo(0);
    }

    // unit tests for getDropById method
    @DisplayName("GetDropByIdTest")
    @Test
    public void givenDropId_whenGetDropById_thenReturnDropObject(){

        //given
        given(dropRepository.findById(1L)).willReturn(Optional.of(drop));
        //when
        Drop savedDrop = dropService.getDropById(drop.getDropId()).get();
        //then
        assertThat(savedDrop).isNotNull();
    }

    // unit test for updateDrop method
    @DisplayName("updateDropTest")
    @Test
    public void givenDropObject_whenUpdateDrop_thenReturnUpdatedDrop(){

        //given
        given(dropRepository.save(drop)).willReturn(drop);
        drop.setDropTitle("Make it 25 locks");
        drop.setDemandDesc("I will double the pay offer");
        //when
        Drop updatedDrop = dropService.updateDrop(drop);
        //then
        assertThat(updatedDrop.getDropTitle()).isEqualTo("Make it 25 locks");
        assertThat(updatedDrop.getDemandDesc()).isEqualTo("I will double the pay offer");

    }

    //unit test for deleteDrop method
    @DisplayName("deleteDropTest")
    @Test
    public void givenDropId_whenDeleteDrop_thenNothing(){

        //given
        long dropId = 2L;
        willDoNothing().given(dropRepository).deleteById(dropId);
        //when
        dropService.deleteDrop(dropId);
        //then
        verify(dropRepository, times(1)).deleteById(dropId);
    }
}
