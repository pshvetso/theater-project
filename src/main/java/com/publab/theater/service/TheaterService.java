package com.publab.theater.service;

import com.publab.theater.model.Theater;
import com.publab.theater.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("theaterService")
public class TheaterService {
    private TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public Theater getOne(long id) {
        return theaterRepository.getOne(id);
    }

    public List<Theater> findAll() {
        return theaterRepository.findAll();
    }

    public List<Theater> getByMovieAndDate(Long movie, LocalDateTime date) {
        return theaterRepository.getByMovie(movie, date);
    }
}
