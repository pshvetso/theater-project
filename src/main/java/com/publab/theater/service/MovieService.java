package com.publab.theater.service;

import com.publab.theater.model.Movie;
import com.publab.theater.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("movieService")
public class MovieService {
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getOne(long id) {
        return movieRepository.getOne(id);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<Movie> getByTheaterAndDate(Long theater, LocalDateTime date) {
        return movieRepository.getByTheaterAndDate(theater, date);
    }
}
