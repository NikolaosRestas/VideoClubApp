package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.PsGames;
import com.example.VideoClubApp.model.dto.PsGamesRequestDto;
import com.example.VideoClubApp.service.PsGamesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/PsGames")
public class PsGamesController {
    PsGamesService psGamesService;

    public PsGamesController(PsGamesService psGamesService){
        this.psGamesService = psGamesService;
    }

    @GetMapping
    public List<PsGames> getAllPsGames(){
        return psGamesService.getAllPsGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsGames> getPsGameById(@PathVariable("id") Long id){
        return new ResponseEntity<>(psGamesService.getPsGameById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deletePsGameById(@PathVariable("id") Long id){
        psGamesService.deletePsGameById(id);
        return true;
    }
    @DeleteMapping("/delete")
    public boolean deleteAllPsGames(){
        psGamesService.deleteAllPsGames();
        return true;
    }

    @PostMapping("/add")
    public ResponseEntity<PsGames> addPsGame(@RequestBody PsGamesRequestDto psGamesRequestDto){
        System.out.println("PsGame Created");
        return new ResponseEntity<PsGames>(psGamesService.insertPsGame(psGamesRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public PsGames updatePsGame(@RequestBody PsGamesRequestDto psGamesRequestDto, @PathVariable("id") Long id){
        return psGamesService.updatePsGame(psGamesRequestDto,id);
    }
}
