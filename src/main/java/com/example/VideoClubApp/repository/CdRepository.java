package com.example.VideoClubApp.repository;

import com.example.VideoClubApp.model.Cd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdRepository extends JpaRepository<Cd,Long> {
}
