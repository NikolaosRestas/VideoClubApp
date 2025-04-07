package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Staff;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.service.VideoClubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class VideoClubControllerTest {

    @InjectMocks
    VideoClubController videoClubController;
    @Mock
    VideoClubService videoClubService;

    @Test
    void getAllVideoClubs(){
        List<VideoClub> videoClubList = new ArrayList<VideoClub>();

        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        videoClubList.add(videoClub);

        when(videoClubService.getAllVideoClubs()).thenReturn(videoClubList);

        ResponseEntity<List<VideoClub>> response = videoClubController.getVideoClubs();

        assertThat(Objects.requireNonNull(response.getBody()).get(0).getId()).isNotNull();
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("BroadCast");
        assertThat(response.getBody().get(0).getPhone()).isEqualTo("aaa");
    }

    @Test
    void getVideoClubById(){
        VideoClub videoClubRequest = new VideoClub(1L,"BroadCast","aaa");
        VideoClub videoClubResponse = new VideoClub(1L,"BroadCast","aaa");

        when(videoClubService.findVideoClubById(videoClubRequest.getId())).thenReturn(videoClubResponse);

        ResponseEntity<VideoClub> response = videoClubController.getVideoClub(videoClubRequest.getId());

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("BroadCast");
        assertThat(response.getBody().getPhone()).isEqualTo("aaa");
    }

    @Test
    void insertVideoClub(){
        VideoClub videoClubRequest = new VideoClub(1L,"BroadCast","aaa");
        VideoClub videoClubResponse = new VideoClub(1L,"BroadCast","aaa");

        when(videoClubService.insertVideoClub(videoClubRequest)).thenReturn(videoClubResponse);
        ResponseEntity<VideoClub> response = videoClubController.addVideoClub(videoClubRequest);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("BroadCast");
        assertThat(response.getBody().getPhone()).isEqualTo("aaa");
    }

    @Test
    void updateVideoClub(){
        VideoClub videoClubRequest = new VideoClub(1L,"BroadCast","aaa");
        VideoClub videoClubResponse = new VideoClub(1L,"BroadCast","bbb");

        when(videoClubService.updateVideoClub(videoClubRequest)).thenReturn(videoClubResponse);

        ResponseEntity<VideoClub> response = videoClubController.updateVideoClub(videoClubRequest);


        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("BroadCast");
        assertThat(response.getBody().getPhone()).isEqualTo("bbb");

    }

    @Test
    void deleteVideoClubById() {
        Long id = 1L;
        VideoClub videoClub = new VideoClub(1L, "BroadCast", "aaa");

        when(videoClubService.deleteVideoClubById(id)).thenReturn("Deleted successfully");

        ResponseEntity response = videoClubController.deleteVideoClubById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
