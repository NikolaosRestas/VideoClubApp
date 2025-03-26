package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Staff;
import com.example.VideoClubApp.model.dto.StaffRequestDto;
import com.example.VideoClubApp.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService){
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff(){
        return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable("id") Long id){
        System.out.println("Staff has been found");
        return new ResponseEntity<>(staffService.findStaffById(id),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public boolean deleteAllStaff(){
        staffService.deleteAllStaff();
        System.out.println("All Staff deleted");
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteStaffById(@PathVariable("id") Long id){
        staffService.deleteStaffById(id);
        System.out.println("Staff deleted");
        return true;
    }

    @PostMapping("/add")
    public ResponseEntity<Staff> addStaff( @Valid @RequestBody StaffRequestDto staff){
        final Staff createdStaff = staffService.insertStaff(staff);
        System.out.println("Staff has been created");
        return new ResponseEntity<>(createdStaff,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Staff> updatedStaff(@Valid @RequestBody StaffRequestDto staff, @PathVariable("id") Long id){
        Staff updatedStaff = staffService.updateStaff(staff,id);
        return new ResponseEntity<>(updatedStaff,HttpStatus.OK);
    }
}
