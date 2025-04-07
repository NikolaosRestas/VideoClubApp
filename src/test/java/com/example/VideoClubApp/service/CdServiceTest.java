package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Cd;
import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CdRequestDto;
import com.example.VideoClubApp.repository.CdRepository;
import com.example.VideoClubApp.repository.CustomerRepository;
import com.example.VideoClubApp.repository.VideoClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class CdServiceTest {
    @InjectMocks
    VideoClubService videoClubService;
    @InjectMocks
    CustomerService customerService;

    @InjectMocks
    CdService cdService;
    @Mock
    CdRepository cdRepository;
    @Mock
    VideoClubRepository videoClubRepository;
    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp(){
        videoClubService = new VideoClubService(videoClubRepository);
        customerService = new CustomerService(customerRepository,videoClubService);
        cdService = new CdService(cdRepository,videoClubService,customerService);
    }

    @Test
    void getCds(){
        List<Cd> cds = new ArrayList<>();
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);
        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdRepository.findAll()).thenReturn(cds);
        cds.add(cd);

        List<Cd> response = cdRepository.findAll();

        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("Krystalla");
        assertThat(response.get(0).getArtist()).isEqualTo("Kiamos");
        assertThat(response.get(0).getVideoClub()).isEqualTo(videoClub);
        assertThat(response.get(0).getCustomer()).isEqualTo(customer);

    }

    @Test
    void getCdById(){

        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);
        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdRepository.findById(1L)).thenReturn(Optional.of(cd));

        Cd response = cdService.findCdById(1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Krystalla");
        assertThat(response.getArtist()).isEqualTo("Kiamos");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void insertCd(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        CdRequestDto cdRequestDto = new CdRequestDto("Krystalla","Kiamos",videoClub.getId(),customer.getId());

        Cd response = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        Cd cd = Cd.builder()
                .id(null)
                .name(cdRequestDto.getName())
                .artist(cdRequestDto.getArtist())
                .videoClub(videoClub)
                .customer(customer).build();

        when(cdRepository.save(any())).thenReturn(cd);
        when(videoClubRepository.findById(cdRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));
        when(customerRepository.findById(cdRequestDto.getCustomerId())).thenReturn(Optional.of(customer));

        cdService.insertCd(cdRequestDto);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Krystalla");
        assertThat(response.getArtist()).isEqualTo("Kiamos");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);

    }

    @Test
    void updateCd(){
        Long id=1L;
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        CdRequestDto cdRequestDto = new CdRequestDto("Krystalla","Kiamos",videoClub.getId(),customer.getId());

        Cd response = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        Cd cd = Cd.builder()
                .id(null)
                .name(cdRequestDto.getName())
                .artist(cdRequestDto.getArtist())
                .videoClub(videoClub)
                .customer(customer).build();

        when(cdRepository.findById(1L)).thenReturn(Optional.of(response));
        when(videoClubRepository.findById(cdRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));
        when(customerRepository.findById(cdRequestDto.getCustomerId())).thenReturn(Optional.of(customer));

        cdService.updateCd(cdRequestDto,1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Krystalla");
        assertThat(response.getArtist()).isEqualTo("Kiamos");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }
}
