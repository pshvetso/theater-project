package com.publab.theater.controller;

import com.publab.theater.model.Seat;
import com.publab.theater.model.Session;
import com.publab.theater.service.BookingService;
import com.publab.theater.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class BookingRestController {
    private final BookingService bookingService;

    private final SessionService sessionService;

    @Autowired
    public BookingRestController(BookingService bookingService, SessionService sessionService) {
        this.bookingService = bookingService;
        this.sessionService = sessionService;
    }

    @RequestMapping("/book")
    public Seat book(@RequestParam(value = "session") Long session_id) {
        Session session = sessionService.getOne(session_id);

        if (session.getSeats().size() != 0) {
            bookingService.deleteAll(session.getSeats());
            return null;
        } else {
            return bookingService.bookSeat(new Seat(session));
        }
    }

    @RequestMapping("/movie-sessions")
    public Map<Long, List<Session>> movieSessions(@RequestParam(value = "id") Long movie,
                                                  @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return sessionService.getPerTheaterByMovieAndDate(movie, date.atStartOfDay());

    }

    @RequestMapping("/theater-sessions")
    public Map<Long, List<Session>> theaterSessions(@RequestParam(value = "id") Long theater,
                                                    @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return sessionService.getPerMovieByTheaterAndDate(theater, date.atStartOfDay());
    }


}
