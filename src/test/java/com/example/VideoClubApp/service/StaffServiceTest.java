package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Staff;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.StaffRequestDto;
import com.example.VideoClubApp.repository.StaffRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {
    @InjectMocks
    VideoClubService videoClubService;
    @InjectMocks
    StaffService staffService;

    @Mock
    StaffRepository staffRepository;
    @Mock
    VideoClubRepository videoClubRepository;

    @BeforeEach
    void setUp() {
       videoClubService = new VideoClubService(videoClubRepository);
       staffService = new StaffService(staffRepository,videoClubService);
    }


    @Test
    void getStaff(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staff = new Staff(1L,"Nikos","aa",videoClub);

        List<Staff> staffs = new ArrayList<>();


        when(staffRepository.findAll()).thenReturn(staffs);
        staffs.add(staff);

        List<Staff> response = staffService.getAllStaff();
        assertThat(response.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void getStaffById(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Staff staffRequest = new Staff(1L,"Nikos","aa",videoClub);
        Staff staffResponse = new Staff(1L,"Nikos","aa",videoClub);

        when(staffRepository.findById(staffRequest.getId())).thenReturn(Optional.of(staffResponse));

        Staff response = staffService.findStaffById(staffRequest.getId());

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Nikos");
        assertThat(response.getPhone()).isEqualTo("aa");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void addStaff(){

        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        StaffRequestDto staffRequestDto = new StaffRequestDto("Nikos","aaa",1L);

        Staff staff = Staff.builder()
                .id(null)
                .name(staffRequestDto.getName())
                .phone(staffRequestDto.getPhone())
                .videoClub(videoClub).build();

        Staff response = new Staff(1L,"Nikos","aaa",staff.getVideoClub());

        when(staffRepository.save(any())).thenReturn(response);
        when(videoClubRepository.findById(staffRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));

        staffService.insertStaff(staffRequestDto);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Nikos");
        assertThat(response.getPhone()).isEqualTo("aaa");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void updateStaff(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        StaffRequestDto staffRequestDto = new StaffRequestDto("Nikos","aaa",1L);

        Staff staff = Staff.builder()
                .id(null)
                .name(staffRequestDto.getName())
                .phone(staffRequestDto.getPhone())
                .videoClub(videoClub).build();

        Staff response = new Staff(1L,"Nikos","bbb",staff.getVideoClub());

        when(staffRepository.findById(1L)).thenReturn(Optional.of(staff));
        when(videoClubRepository.findById(staffRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));

        staffService.updateStaff(staffRequestDto,1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Nikos");
        assertThat(response.getPhone()).isEqualTo("bbb");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);

    }
}
