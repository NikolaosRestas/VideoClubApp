package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.Movie;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.MovieRequestDto;
import com.example.VideoClubApp.repository.CustomerRepository;
import com.example.VideoClubApp.repository.MovieRepository;
import com.example.VideoClubApp.repository.VideoClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @InjectMocks
    VideoClubService videoClubService;
    @InjectMocks
    CustomerService customerService;
    @InjectMocks
    MovieService movieService;
    @Mock
    VideoClubRepository videoClubRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    MovieRepository movieRepository;

    @BeforeEach
    void setUp(){
        videoClubService = new VideoClubService(videoClubRepository);
        customerService = new CustomerService(customerRepository,videoClubService);
        movieService = new MovieService(movieRepository,videoClubService,customerService);
    }

    @Test
    void getMovies(){
        List<Movie> movies = new ArrayList<>();
        VideoClub videoClub = new VideoClub(1L,"BroadCast","695521548");
        Customer customer = new Customer(1L,"Nikos Restas","aa","@gmail.com",videoClub);
        Movie movie = new Movie(1L,"John Wick","2015",videoClub,customer);

        when(movieRepository.findAll()).thenReturn(movies);
        movies.add(movie);

        List<Movie> response = movieService.getAllMovies();

        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getTitle()).isEqualTo("John Wick");
        assertThat(response.get(0).getYear()).isEqualTo("2015");
        assertThat(response.get(0).getVideoClub()).isEqualTo(videoClub);
        assertThat(response.get(0).getCustomer()).isEqualTo(customer);
    }

    @Test
    void getMovieById(){
        Long id=1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","695521548");
        Customer customer = new Customer(1L,"Nikos Restas","aa","@gmail.com",videoClub);
        Movie movie = new Movie(1L,"John Wick","2015",videoClub,customer);

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        Movie response = movieService.findMovieById(id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("John Wick");
        assertThat(response.getYear()).isEqualTo("2015");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void insertMovie(){
        VideoClub videoClub = new VideoClub(1L,"BroadCast","695521548");
        Customer customer = new Customer(1L,"Nikos Restas","aa","@gmail.com",videoClub);
        MovieRequestDto movieRequestDto = new MovieRequestDto("John Wick","2015",videoClub.getId(),customer.getId());

        Movie movie = Movie.builder()
                .id(null)
                .title(movieRequestDto.getTitle())
                .year(movieRequestDto.getYear())
                .videoClub(videoClub)
                .customer(customer).build();

        when(movieRepository.save(any())).thenReturn(movie);
        when(videoClubRepository.findById(movieRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));
        when(customerRepository.findById(movieRequestDto.getCustomerId())).thenReturn(Optional.of(customer));

        Movie response = new Movie(1L,"John Wick","2015",videoClub,customer);

        movieService.insertMovie(movieRequestDto);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("John Wick");
        assertThat(response.getYear()).isEqualTo("2015");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void updateMovie(){
        Long id=1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","695521548");
        Customer customer = new Customer(1L,"Nikos Restas","aa","@gmail.com",videoClub);
        MovieRequestDto movieRequestDto = new MovieRequestDto("John Wick","2015",videoClub.getId(),customer.getId());

        Movie movie = Movie.builder()
                .id(null)
                .title(movieRequestDto.getTitle())
                .year(movieRequestDto.getYear())
                .videoClub(videoClub)
                .customer(customer).build();

        when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(movie));
        when(videoClubRepository.findById(movieRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));
        when(customerRepository.findById(movieRequestDto.getCustomerId())).thenReturn(Optional.of(customer));

        Movie response = new Movie(1L,"John Wick","2015",videoClub,customer);

        movieService.updateMovie(movieRequestDto,1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("John Wick");
        assertThat(response.getYear()).isEqualTo("2015");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

}
