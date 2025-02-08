package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Staff;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.StaffRequestDto;
import com.example.VideoClubApp.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final VideoClubService videoClubService;

    public StaffService(StaffRepository staffRepository, VideoClubService videoClubService){
        this.staffRepository = staffRepository;
        this.videoClubService = videoClubService;
    }

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    public boolean deleteAllStaff(){
        staffRepository.deleteAll();
        System.out.println("All staff have been deleted");
        return true;
    }

    public boolean deleteStaffById(Long id){
        staffRepository.deleteById(id);
        System.out.println("Staff with id: %s"+id+"has been deleted");
        return true;
    }

    public Staff insertStaff(StaffRequestDto staffRequestDto){
        final VideoClub videoClub = videoClubService.findVideoClubById(staffRequestDto.getVideoClubId());

        Staff staff = Staff.builder()
                .id(null)
                .name(staffRequestDto.getName())
                .phone(staffRequestDto.getPhone())
                .videoClub(videoClub).build();

        return staffRepository.save(staff);
    }
}
