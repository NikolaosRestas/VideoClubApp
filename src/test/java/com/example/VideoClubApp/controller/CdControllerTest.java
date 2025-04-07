package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Cd;
import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CdRequestDto;
import com.example.VideoClubApp.service.CdService;
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

@ExtendWith(MockitoExtension.class)
public class CdControllerTest {
    @InjectMocks
    CdController cdController;

    @Mock
    CdService cdService;

    @Test
    void getCds(){
        List<Cd> cds = new ArrayList<>();
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdService.getAllCds()).thenReturn(cds);
        cds.add(cd);

        ResponseEntity<List<Cd>> response = cdController.getCds();

        assertThat(Objects.requireNonNull(response.getBody()).get(0).getId()).isNotNull();
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Krystalla");
        assertThat(response.getBody().get(0).getArtist()).isEqualTo("Kiamos");
        assertThat(response.getBody().get(0).getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getBody().get(0).getCustomer()).isEqualTo(customer);
    }

    @Test
    void getCdById(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdService.findCdById(1L)).thenReturn(cd);
        ResponseEntity<Cd> response = cdController.getCdById(1L);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Krystalla");
        assertThat(response.getBody().getArtist()).isEqualTo("Kiamos");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getBody().getCustomer()).isEqualTo(customer);
    }

    @Test
    void insertCd(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        CdRequestDto cdRequestDto = new CdRequestDto("Krystalla","Kiamos",videoClub.getId(),customer.getId());

        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdService.insertCd(cdRequestDto)).thenReturn(cd);
        ResponseEntity<Cd> response = cdController.addCd(cdRequestDto);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Krystalla");
        assertThat(response.getBody().getArtist()).isEqualTo("Kiamos");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getBody().getCustomer()).isEqualTo(customer);
    }

    @Test
    void updateCd(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        CdRequestDto cdRequestDto = new CdRequestDto("Krystalla","Kiamos",videoClub.getId(),customer.getId());

        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdService.updateCd(cdRequestDto,1L)).thenReturn(cd);
        Cd response = cdController.updateCd(cdRequestDto,1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Krystalla");
        assertThat(response.getArtist()).isEqualTo("Kiamos");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void deleteCdById(){
        Long id = 1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"Nikos","bbb","@gmail.com",videoClub);
        Cd cd = new Cd(1L,"Krystalla","Kiamos",videoClub,customer);

        when(cdService.deleteCdById(id)).thenReturn(true);

        Boolean response = cdController.deleteCdById(id);
        assertThat(response).isTrue();
    }
}
