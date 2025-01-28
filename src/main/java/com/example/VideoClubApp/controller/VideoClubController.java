package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.service.VideoClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoClubController {
    private final VideoClubService videoClubService;
    public VideoClubController(VideoClubService videoClubService){
        this.videoClubService = videoClubService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoClub> getVideoClub(@PathVariable("id") Long id){
        return new ResponseEntity<VideoClub>(videoClubService.findVideoClubById(id), HttpStatus.OK);
    }
}
