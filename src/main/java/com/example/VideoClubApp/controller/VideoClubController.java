package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.service.VideoClubService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/videoClubs")
public class VideoClubController {
    private final VideoClubService videoClubService;
    public VideoClubController(VideoClubService videoClubService){
        this.videoClubService = videoClubService;
    }


    @GetMapping
    public ResponseEntity<List<VideoClub>> getVideoClubs(){
        return new ResponseEntity<List<VideoClub>>(videoClubService.getAllVideoClubs(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoClub> getVideoClub(@PathVariable("id") Long id){
        return new ResponseEntity<VideoClub>(videoClubService.findVideoClubById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<VideoClub> addVideoClub(@Valid @RequestBody VideoClub videoClub){
        final VideoClub createdVideoClub = videoClubService.insertVideoClub(videoClub);
        return new ResponseEntity<VideoClub>(createdVideoClub,HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAll")
    public boolean deleteAllVideoClubs(){
        videoClubService.deleteAllVideoClubs();
        System.out.println("=====================================");
        System.out.println("ALL VideoClubs Deleted from Database");
        System.out.println("=====================================");
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVideoClubById(@PathVariable("id") Long id){
        videoClubService.deleteVideoClubById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoClub> updateVideoClub(@Valid @RequestBody VideoClub videoClub){
        videoClubService.updateVideoClub(videoClub);
        VideoClub updatedVideoClub = videoClubService.updateVideoClub(videoClub);
        return new ResponseEntity<>(updatedVideoClub,HttpStatus.OK);
    }
}
