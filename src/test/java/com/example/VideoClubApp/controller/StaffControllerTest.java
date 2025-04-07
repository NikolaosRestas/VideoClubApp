package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Staff;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.StaffRequestDto;
import com.example.VideoClubApp.service.StaffService;
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
public class StaffControllerTest {
    @InjectMocks
    StaffController staffController;
    @Mock
    StaffService staffService;

    @Test
    void getStaff(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staff = new Staff(1L,"Nikos","aa",videoClub);
        List<Staff> staffList = new ArrayList<Staff>();

        when(staffService.getAllStaff()).thenReturn(staffList);
        staffList.add(staff);

        ResponseEntity<List<Staff>> response = staffController.getAllStaff();

        assertThat(Objects.requireNonNull(response.getBody()).get(0).getId()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Nikos");
        assertThat(response.getBody().get(0).getPhone()).isEqualTo("aa");
        assertThat(response.getBody().get(0).getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void getStaffById(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staff = new Staff(1L,"Nikos","aaa",videoClub);

        when(staffService.findStaffById(1L)).thenReturn(staff);
        ResponseEntity<Staff> response = staffController.getStaffById(1L);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getPhone()).isEqualTo("aaa");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void insertStaff(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staff = new Staff(1L,"Nikos","aaa",videoClub);

        StaffRequestDto staffRequestDto = new StaffRequestDto("Nikos","aaa",1L);

        when(staffService.insertStaff(staffRequestDto)).thenReturn(staff);

        ResponseEntity<Staff> response = staffController.addStaff(staffRequestDto);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getPhone()).isEqualTo("aaa");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void updateStaff(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staff = new Staff(1L,"Nikos","bbb",videoClub);

        StaffRequestDto staffRequestDto = new StaffRequestDto("Nikos","aaa",1L);

        when(staffService.updateStaff(staffRequestDto,1L)).thenReturn(staff);

        ResponseEntity<Staff> response = staffController.updatedStaff(staffRequestDto,1L);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getPhone()).isEqualTo("bbb");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void deleteStaffById(){
        Long id = 1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staff = new Staff(1L,"Nikos","bbb",videoClub);

        when(staffService.deleteStaffById(id)).thenReturn(true);

        Boolean response = staffController.deleteStaffById(id);

        assertThat(response).isTrue();

    }
}
