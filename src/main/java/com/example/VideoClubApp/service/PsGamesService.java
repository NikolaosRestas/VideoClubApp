package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.PsGames;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.PsGamesRequestDto;
import com.example.VideoClubApp.repository.PsGamesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsGamesService {
    private PsGamesRepository psGamesRepository;
    private final VideoClubService videoClubService;

    private final CustomerService customerService;

    public PsGamesService(PsGamesRepository psGamesRepository, VideoClubService videoClubService,CustomerService customerService){
        this.psGamesRepository = psGamesRepository;
        this.videoClubService = videoClubService;
        this.customerService = customerService;
    }
    public List<PsGames> getAllPsGames(){
        return psGamesRepository.findAll();
    }

    public boolean deletePsGameById(Long id){
        psGamesRepository.deleteById(id);
        return true;
    }

    public boolean deleteAllPsGames(){
        psGamesRepository.deleteAll();
        return true;
    }

    public PsGames getPsGameById(Long id){
        return psGamesRepository.findById(id).orElse(null);
    }

    public PsGames insertPsGame(PsGamesRequestDto psGamesRequestDto){
        final VideoClub videoClub = videoClubService.findVideoClubById(psGamesRequestDto.getVideoClubId());
        final Customer customer = customerService.findCustomerById(psGamesRequestDto.getCustomerId());
        final PsGames psGameSaved = PsGames.builder()
                .id(null)
                .title(psGamesRequestDto.getTitle())
                .company(psGamesRequestDto.getCompany())
                .console(psGamesRequestDto.getConsole())
                .videoClub(videoClub)
                .customer(customer).build();

        if(psGamesRepository!=null){
            System.out.println("PsGame created");
        }else{
            return null;
        }
        return psGamesRepository.save(psGameSaved);
    }

    public PsGames updatePsGame(PsGamesRequestDto psGamesRequestDto,Long id){
        final PsGames updatedPsGame = getPsGameById(id);
        updatedPsGame.setTitle(psGamesRequestDto.getTitle());
        updatedPsGame.setCompany(psGamesRequestDto.getCompany());
        updatedPsGame.setConsole(psGamesRequestDto.getConsole());

        final VideoClub videoClub = videoClubService.findVideoClubById(psGamesRequestDto.getVideoClubId());
        final Customer customer = customerService.findCustomerById(psGamesRequestDto.getCustomerId());

        psGamesRepository.save(updatedPsGame);
        System.out.println("PsGame Updated");
        return updatedPsGame;
    }
}
