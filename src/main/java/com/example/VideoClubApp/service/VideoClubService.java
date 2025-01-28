package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.repository.VideoClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoClubService {
    private VideoClubRepository videoClubRepository;

    public VideoClubService(VideoClubRepository videoClubRepository){
        this.videoClubRepository = videoClubRepository;
    }
    public List<VideoClub> getAllVideoClubs(){
        return this.videoClubRepository.findAll();
    }

    public VideoClub findVideoClubById(Long id){
        return videoClubRepository.findById(id).orElse(null);
    }

    public boolean deleteAllVideoClubs(){
        videoClubRepository.deleteAll();
        return true;
    }

    public String deleteVideoClubById(Long id){
        VideoClub videoClub = videoClubRepository.findById(id).orElse(null);
        if(videoClub == null){
            return "VideoClub does not exists";
        }
        videoClubRepository.deleteById(id);
        return "VideoClub deleted successfully";
    }

    public VideoClub insertVideoClub(VideoClub videoClub){
        videoClub.setId(null);
        return videoClubRepository.save(videoClub);
    }

    public VideoClub updateVideoClub(VideoClub videoClub){
        VideoClub savedVideoClub = findVideoClubById(videoClub.getId());
        savedVideoClub.setName(videoClub.getName());
        savedVideoClub.setPhone(videoClub.getPhone());
        return savedVideoClub;
    }

}
