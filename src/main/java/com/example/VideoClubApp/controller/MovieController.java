package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Movie;
import com.example.VideoClubApp.model.dto.MovieRequestDto;
import com.example.VideoClubApp.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})

@RequestMapping("/movies")
public class MovieController {
    final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id){
        return new ResponseEntity<Movie>(movieService.findMovieById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieRequestDto movieRequestDto){
        return new ResponseEntity<Movie>(movieService.insertMovie(movieRequestDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public boolean deleteAllMovies(){
        movieService.deleteAllMovies();
        System.out.println("All Movies have been deleted");
        return true;
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteMovieById(@PathVariable("id") Long id){
        movieService.deleteMovieById(id);
        System.out.println("Movie with id:"+id + " has been deleted");
        return true;
    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@Valid @RequestBody MovieRequestDto movieRequestDto, @PathVariable("id") Long id){
        return movieService.updateMovie(movieRequestDto,id);
    }

}
