package com.example.VideoClubApp.repository;

import com.example.VideoClubApp.model.PsGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsGamesRepository extends JpaRepository<PsGames,Long> {
}
