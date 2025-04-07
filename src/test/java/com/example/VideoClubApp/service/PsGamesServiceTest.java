package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.PsGames;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.PsGamesRequestDto;
import com.example.VideoClubApp.repository.CustomerRepository;
import com.example.VideoClubApp.repository.PsGamesRepository;
import com.example.VideoClubApp.repository.VideoClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PsGamesServiceTest {
    @InjectMocks
    CustomerService customerService;

    @InjectMocks
    VideoClubService videoClubService;

    @InjectMocks
    PsGamesService psGamesService;

    @Mock
    CustomerRepository customerRepository;
    @Mock
    VideoClubRepository videoClubRepository;
    @Mock
    PsGamesRepository psGamesRepository;

    @BeforeEach
    void setUp(){
        videoClubService = new VideoClubService(videoClubRepository);
        customerService = new CustomerService(customerRepository,videoClubService);
        psGamesService = new PsGamesService(psGamesRepository,videoClubService,customerService);
    }

    @Test
    void getAllPsGames(){
        List<PsGames> psGamesList = new ArrayList<>();
        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGames psGames = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        when(psGamesRepository.findAll()).thenReturn(psGamesList);
        psGamesList.add(psGames);

        List<PsGames> response = psGamesService.getAllPsGames();

        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getTitle()).isEqualTo("Witcher");
        assertThat(response.get(0).getCompany()).isEqualTo("RedProject");
        assertThat(response.get(0).getConsole()).isEqualTo("ps5");
        assertThat(response.get(0).getVideoClub()).isEqualTo(videoClub);
        assertThat(response.get(0).getCustomer()).isEqualTo(customer);
    }

    @Test
    void getPsGamesById(){
        Long id=1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGames psGames = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        when(psGamesRepository.findById(id)).thenReturn(Optional.of(psGames));

        PsGames response = psGamesService.getPsGameById(id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Witcher");
        assertThat(response.getCompany()).isEqualTo("RedProject");
        assertThat(response.getConsole()).isEqualTo("ps5");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void insertPsGames(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","965524856");
        Customer customer = new Customer(1L,"NikosRestas","695525846","@gmail.com",videoClub);
        PsGamesRequestDto psGamesRequestDto = new PsGamesRequestDto("Witcher","RedProject","ps5",videoClub.getId(),customer.getId());

        PsGames psGame1 = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        PsGames psGame = PsGames.builder()
                .id(null)
                .title(psGamesRequestDto.getTitle())
                .company(psGamesRequestDto.getCompany())
                .console(psGamesRequestDto.getConsole())
                .videoClub(videoClub)
                .customer(customer).build();

        when(psGamesRepository.save(any())).thenReturn(psGame1);
        when(videoClubRepository.findById(psGamesRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));
        when(customerRepository.findById(psGamesRequestDto.getCustomerId())).thenReturn(Optional.of(customer));

        PsGames response = psGamesService.insertPsGame(psGamesRequestDto);

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
        PsGamesRequestDto psGamesRequestDto = new PsGamesRequestDto("Witcher","ps5","RedProject",videoClub.getId(),customer.getId());

        PsGames psGame1 = new PsGames(1L,"Witcher","RedProject","ps5",videoClub,customer);

        PsGames psGame = PsGames.builder()
                .id(null)
                .title(psGamesRequestDto.getTitle())
                .company(psGamesRequestDto.getCompany())
                .console(psGamesRequestDto.getConsole())
                .videoClub(videoClub)
                .customer(customer).build();

        when(psGamesRepository.findById(id)).thenReturn(Optional.of(psGame1));
        when(videoClubRepository.findById(psGamesRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));
        when(customerRepository.findById(psGamesRequestDto.getCustomerId())).thenReturn(Optional.of(customer));

        PsGames response = psGamesService.updatePsGame(psGamesRequestDto,id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Witcher");
        assertThat(response.getCompany()).isEqualTo("RedProject");
        assertThat(response.getConsole()).isEqualTo("ps5");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);

    }
}
