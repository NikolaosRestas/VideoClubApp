package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Cd;
import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.Movie;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CdRequestDto;
import com.example.VideoClubApp.model.dto.MovieRequestDto;
import com.example.VideoClubApp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @InjectMocks
    MovieController movieController;
    @Mock
    MovieService movieService;

    @Test
    void getMovies(){
        List<Movie> movieList = new ArrayList<>();
        VideoClub videoClub = new VideoClub(1L,"BroadCast","695521548");
        Customer customer = new Customer(1L,"Nikos Restas","aa","@gmail.com",videoClub);
        Movie movie = new Movie(1L,"John Wick","2015",videoClub,customer);

        when(movieService.getAllMovies()).thenReturn(movieList);
        movieList.add(movie);

        List<Movie> response = movieController.getAllMovies();

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

        when(movieService.findMovieById(id)).thenReturn(movie);
        ResponseEntity<Movie> response = movieController.getMovieById(id);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getTitle()).isEqualTo("John Wick");
        assertThat(response.getBody().getYear()).isEqualTo("2015");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getBody().getCustomer()).isEqualTo(customer);
    }

    @Test
    void insertMovie(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        MovieRequestDto movieRequestDto = new MovieRequestDto("John Wick","2015",videoClub.getId(),customer.getId());

        Movie movie = new Movie(1L,"John Wick","2015",videoClub,customer);

        when(movieService.insertMovie(movieRequestDto)).thenReturn(movie);

        ResponseEntity<Movie> response = movieController.addMovie(movieRequestDto);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getTitle()).isEqualTo("John Wick");
        assertThat(response.getBody().getYear()).isEqualTo("2015");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getBody().getCustomer()).isEqualTo(customer);
    }
    @Test
    void updateMovie(){
        Long id=1L;
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        MovieRequestDto movieRequestDto = new MovieRequestDto("John Wick","2015",videoClub.getId(),customer.getId());

        Movie movie = new Movie(1L,"John Wick","2015",videoClub,customer);

        when(movieService.updateMovie(movieRequestDto,id)).thenReturn(movie);

        Movie response = movieController.updateMovie(movieRequestDto,id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("John Wick");
        assertThat(response.getYear()).isEqualTo("2015");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);
        assertThat(response.getCustomer()).isEqualTo(customer);
    }

    @Test
    void deleteCdById(){
        Long id = 1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"Nikos","bbb","@gmail.com",videoClub);
        Movie movie = new Movie(1L,"John Wick","2015",videoClub,customer);

        when(movieService.deleteMovieById(id)).thenReturn(true);

        Boolean response = movieController.deleteMovieById(id);
        assertThat(response).isTrue();
    }
}
