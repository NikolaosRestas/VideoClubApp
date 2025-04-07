package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.repository.VideoClubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class VideoClubServiceTest {

    @InjectMocks
    VideoClubService videoClubService;

    @Mock
    VideoClubRepository videoClubRepository;

    @Test
    void getAllVideoClubs(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        List<VideoClub> videoClubs = new ArrayList<VideoClub>();

        when(videoClubRepository.findAll()).thenReturn(videoClubs);
        videoClubs.add(videoClub);

        List<VideoClub> response = videoClubService.getAllVideoClubs();
        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("BroadCast");
        assertThat(response.get(0).getPhone()).isEqualTo("aaa");
    }

    @Test
    void getVideoClubById(){
        Long id=1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");

        when(videoClubRepository.findById(id)).thenReturn(Optional.of(videoClub));

        VideoClub response = videoClubService.findVideoClubById(id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("BroadCast");
        assertThat(response.getPhone()).isEqualTo("aaa");

    }

    @Test
    void addVideoClub(){
        VideoClub videoClubRequest = new VideoClub(1L,"BroadCast","aaa");
        VideoClub videoClubResponse = new VideoClub(1L,"BroadCast","aaa");

        when(videoClubRepository.save(videoClubRequest)).thenReturn(videoClubResponse);

        VideoClub response = videoClubService.insertVideoClub(videoClubRequest);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("BroadCast");
        assertThat(response.getPhone()).isEqualTo("aaa");
    }

    @Test
    void updateVideoClub(){
        Long id = 1L;
        VideoClub videoClubRequest = new VideoClub(1L,"BroadCast","aaa");
        VideoClub videoClubResponse = new VideoClub(1L,"BroadCast","bbb");


        when(videoClubRepository.save(videoClubRequest)).thenReturn(videoClubResponse);
        List<VideoClub> videoClubs = new ArrayList<VideoClub>();
        videoClubs.add(videoClubResponse);
        VideoClub response = videoClubService.insertVideoClub(videoClubRequest);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("BroadCast");
        assertThat(response.getPhone()).isEqualTo("bbb");

    }
}
