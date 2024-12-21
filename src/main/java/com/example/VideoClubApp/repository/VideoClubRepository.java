package com.example.VideoClubApp.repository;

import com.example.VideoClubApp.model.VideoClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoClubRepository extends JpaRepository<VideoClub,Long> {
}
