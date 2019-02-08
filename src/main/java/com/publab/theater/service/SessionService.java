package com.publab.theater.service;

import com.publab.theater.model.Session;
import com.publab.theater.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("sessionService")
public class SessionService {
    private SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getOne(long id) {
        return sessionRepository.getOne(id);
    }

    public List<Session> getByMovieAndDate(Long movie, LocalDateTime date) {
//        return sessionRepository.findByMovieIdAndTimeOrderByTime(movie, date);
        return sessionRepository.getByMovieAndDate(movie, date);
    }

    public Map<Long, List<Session>> getPerTheaterByMovieAndDate(Long movie, LocalDateTime date) {
        List<Session> sessionsList = getByMovieAndDate(movie, date);

        Map<Long, List<Session>> sessions = sessionsList.stream().collect(
                Collectors.groupingBy(p -> p.getHall().getTheater().getId()));

        return sessions;
    }

    public List<Session> getByTheaterAndDate(Long theater, LocalDateTime date) {
        return sessionRepository.getByTheaterAndDate(theater, date);
    }

    public Map<Long, List<Session>> getPerMovieByTheaterAndDate(Long theater, LocalDateTime date) {
        List<Session> sessionsList = getByTheaterAndDate(theater, date);

        Map<Long, List<Session>> sessions = sessionsList.stream().collect(
                Collectors.groupingBy(p -> p.getMovie().getId()));

        return sessions;
    }

}
