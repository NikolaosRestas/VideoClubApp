package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Cd;
import com.example.VideoClubApp.model.dto.CdRequestDto;
import com.example.VideoClubApp.service.CdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})

@RequestMapping("/cd")
public class CdController {
    private final CdService cdService;

    public CdController(CdService cdService){
        this.cdService = cdService;
    }

    @GetMapping
    public ResponseEntity<List<Cd>> getCds(){
        return new ResponseEntity<List<Cd>>(cdService.getAllCds(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cd> getCdById(@PathVariable("id") Long id){
        return new ResponseEntity<Cd>(cdService.findCdById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cd> addCd(@RequestBody CdRequestDto cdRequestDto){
        final Cd createdCd = cdService.insertCd(cdRequestDto);
        System.out.println("Customer Created");
        return new ResponseEntity<>(createdCd,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public boolean deleteAllCd(){
        cdService.deleteAllCd();
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCdById(@PathVariable("id") Long id){
        cdService.deleteCdById(id);
        return true;
    }

    @PutMapping("/update/{id}")
    public Cd updateCd(@RequestBody CdRequestDto cd, @PathVariable("id") Long id){
        System.out.println("Cd updated successfully");
        return cdService.updateCd(cd,id);
    }
}
