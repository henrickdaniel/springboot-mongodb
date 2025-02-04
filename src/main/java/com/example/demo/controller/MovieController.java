package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> listAll(){
        return new ResponseEntity<List<Movie>>(movieRepository.showMovies(), HttpStatus.OK);
    }

}
