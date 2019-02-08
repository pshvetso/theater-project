package com.publab.theater.controller;

import com.publab.theater.service.MovieService;
import com.publab.theater.service.SessionService;
import com.publab.theater.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Controller
public class IndexController {

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final SessionService sessionService;
    private static LocalDateTime now;

    @Autowired
    public IndexController(MovieService movieService, TheaterService theaterService, SessionService sessionService) {
        now = LocalDateTime.parse("2018-11-09T12:55:00");
        this.movieService = movieService;
        this.theaterService = theaterService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("movies", movieService.findAll());

        return "index";
    }

    @RequestMapping("/movie/{id}")
    public String movie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getOne(id));
        model.addAttribute("theaters", theaterService.getByMovieAndDate(id, now));
        model.addAttribute("sessions", sessionService.getPerTheaterByMovieAndDate(id, now));

        return "movie";
    }

    @RequestMapping("/theaters")
    public String theaters(Model model) {
        model.addAttribute("theaters", theaterService.findAll());

        return "theaters";
    }

    @RequestMapping("/theater/{id}")
    public String theater(@PathVariable Long id, Model model) {
        model.addAttribute("theater", theaterService.getOne(id));
        model.addAttribute("movies", movieService.getByTheaterAndDate(id, now));
        model.addAttribute("sessions", sessionService.getPerMovieByTheaterAndDate(id, now));

        return "theater";
    }

}
