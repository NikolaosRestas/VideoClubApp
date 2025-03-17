package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.Movie;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.MovieRequestDto;
import com.example.VideoClubApp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    private VideoClubService videoClubService;
    private CustomerService customerService;

    public MovieService(MovieRepository movieRepository,VideoClubService videoClubService,CustomerService customerService){
        this.movieRepository = movieRepository;
        this.videoClubService = videoClubService;
        this.customerService = customerService;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie findMovieById(Long id){
        System.out.println("Find Movie By Id:" + id);
        return movieRepository.findById(id).orElse(null);
    }

    public boolean deleteAllMovies(){
        movieRepository.deleteAll();
        return true;
    }

    public boolean deleteMovieById(Long id){
        movieRepository.deleteById(id);
        return true;
    }
    public Movie insertMovie(MovieRequestDto movieRequestDto){
        final VideoClub videoClub = videoClubService.findVideoClubById(movieRequestDto.getVideoClubId());
        final Customer customer = customerService.findCustomerById(movieRequestDto.getCustomerId());

        final Movie movie = Movie.builder()
                .id(null)
                .title(movieRequestDto.getTitle())
                .year(movieRequestDto.getYear())
                .videoClub(videoClub)
                .customer(customer).build();

        Movie savedMovie = movieRepository.save(movie);

        if(movieRepository !=null){
            System.out.println("Movie created");
        }else{
            return null;
        }
        return savedMovie;
    }

    public Movie updateMovie(MovieRequestDto movieRequestDto,Long id){
        final Movie savedMovie = findMovieById(id);
        savedMovie.setTitle(movieRequestDto.getTitle());
        savedMovie.setYear(movieRequestDto.getYear());

        final VideoClub videoClub = videoClubService.findVideoClubById(movieRequestDto.getVideoClubId());
        final Customer customer = customerService.findCustomerById(movieRequestDto.getCustomerId());

        savedMovie.setVideoClub(videoClub);
        savedMovie.setCustomer(customer);

        System.out.println("Movie with id: " + id +" " + "has been updated");

        return movieRepository.save(savedMovie);
    }


}
