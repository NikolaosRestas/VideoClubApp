package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Cd;
import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CdRequestDto;
import com.example.VideoClubApp.repository.CdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CdService {
    private final CdRepository cdRepository;
    private final VideoClubService videoClubService;
    private final CustomerService customerService;

    public CdService(CdRepository cdRepository, VideoClubService videoClubService, CustomerService customerService){
        this.cdRepository = cdRepository;
        this.videoClubService = videoClubService;
        this.customerService = customerService;
    }

    public List<Cd> getAllCds(){
        return this.cdRepository.findAll();
    }

    public Cd findCdById(Long id){
        return this.cdRepository.findById(id).orElse(null);
    }

    public Cd insertCd(CdRequestDto cdRequestDto){
        final VideoClub videoClub = videoClubService.findVideoClubById(cdRequestDto.getVideoClubId());
        final Customer customer = customerService.findCustomerById(cdRequestDto.getCustomerId());

        final Cd cd = Cd.builder()
                .id(null)
                .name(cdRequestDto.getName())
                .artist(cdRequestDto.getArtist())
                .customer(customer)
                .videoClub(videoClub).build();
        if(cdRepository!=null){
            System.out.println("Cd created");
        }else{
            return null;
        }
        return cdRepository.save(cd);
    }

    public boolean deleteAllCd(){
        cdRepository.deleteAll();
        if(cdRepository!=null){
            return false;
        }
        System.out.println("All Cds have deleted");
        return true;
    }

    public boolean deleteCdById(Long id){
        cdRepository.deleteById(id);
        System.out.println("Cd with id: " + id + "is deleted");
        return true;
    }

    public Cd updateCd(CdRequestDto cdRequestDto, Long id){
        final Cd savedCd = findCdById(id);
        savedCd.setName(cdRequestDto.getName());
        savedCd.setArtist(cdRequestDto.getArtist());
        final VideoClub videoClub = videoClubService.findVideoClubById(cdRequestDto.getVideoClubId());
        final Customer customer = customerService.findCustomerById(cdRequestDto.getCustomerId());

        savedCd.setVideoClub(videoClub);
        savedCd.setCustomer(customer);
        System.out.println("Cd updated");
        return cdRepository.save(savedCd);
    }
}
