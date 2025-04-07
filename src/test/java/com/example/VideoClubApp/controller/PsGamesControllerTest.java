package com.example.VideoClubApp.controller;


import com.example.VideoClubApp.model.Cd;
import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.PsGames;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.PsGamesRequestDto;
import com.example.VideoClubApp.service.PsGamesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PsGamesControllerTest {
    @InjectMocks
    PsGamesController psGamesController;

    @Mock
    PsGamesService psGamesService;

    @Test
    void getPsGames(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGames psGames = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);
        List<PsGames> psGamesList = new ArrayList<>();

        when(psGamesService.getAllPsGames()).thenReturn(psGamesList);
        psGamesList.add(psGames);

        List<PsGames> response = psGamesController.getAllPsGames();

        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getTitle()).isEqualTo("Witcher");
        assertThat(response.get(0).getConsole()).isEqualTo("ps5");
        assertThat(response.get(0).getCompany()).isEqualTo("RedProject");
        assertThat(response.get(0).getVideoClub()).isEqualTo(videoClub);
        assertThat(response.get(0).getCustomer()).isEqualTo(customer);
    }

    @Test
    void getPsGameById(){
        Long id=1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGames psGame = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        when(psGamesService.getPsGameById(id)).thenReturn(psGame);

        ResponseEntity<PsGames> response = psGamesController.getPsGameById(id);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getTitle()).isEqualTo("Witcher");
        assertThat(response.getBody().getCompany()).isEqualTo("RedProject");
        assertThat(response.getBody().getConsole()).isEqualTo("ps5");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getBody().getCustomer()).isEqualTo(customer);
    }

    @Test
    void insertPsGame(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGamesRequestDto psGamesRequestDto = new PsGamesRequestDto("Witcher","RedProject","ps5",videoClub.getId(),customer.getId());

        PsGames psGame1 = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        when(psGamesService.insertPsGame(psGamesRequestDto)).thenReturn(psGame1);
        PsGames response = psGamesController.psGamesService.insertPsGame(psGamesRequestDto);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Witcher");
        assertThat(response.getConsole()).isEqualTo("ps5");
        assertThat(response.getCompany()).isEqualTo("RedProject");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void updatePsGame(){
        Long id=1L;

        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGamesRequestDto psGamesRequestDto = new PsGamesRequestDto("Witcher","RedProject","ps5",videoClub.getId(),customer.getId());

        PsGames psGame1 = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        when(psGamesService.updatePsGame(psGamesRequestDto,id)).thenReturn(psGame1);
        PsGames response = psGamesController.psGamesService.updatePsGame(psGamesRequestDto,id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Witcher");
        assertThat(response.getConsole()).isEqualTo("ps5");
        assertThat(response.getCompany()).isEqualTo("RedProject");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void deletePsGameById(){
        Long id = 1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"Nikos","bbb","@gmail.com",videoClub);
        PsGames psGame = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        when(psGamesService.deletePsGameById(id)).thenReturn(true);

        Boolean response = psGamesController.deletePsGameById(id);
        assertThat(response).isTrue();
    }
}
