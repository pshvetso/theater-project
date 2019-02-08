package com.publab.theater.controller;

import com.publab.theater.model.Movie;
import com.publab.theater.model.Session;
import com.publab.theater.model.Theater;
import com.publab.theater.service.MovieService;
import com.publab.theater.service.SessionService;
import com.publab.theater.service.TheaterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class IndexControllerUnitTest {
    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @Mock
    private TheaterService theaterService;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private IndexController indexController;


    @Before
    public void setUp() {
        //viewResolver added to fix
        //javax.servlet.ServletException: Circular view path : would dispatch back to the current handler URL again. Check your ViewResolver setup!
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).setViewResolvers(viewResolver).build();

        Movie movie1 = Movie.builder()
                .id(1L)
                .title("movie1")
                .build();
        when(movieService.findAll()).thenReturn(Arrays.asList(movie1));
        when(movieService.getByTheaterAndDate(eq(1L), Mockito.any(LocalDateTime.class))).thenReturn(Arrays.asList(movie1));
        when(movieService.getOne(1L)).thenReturn(movie1);

        Theater theater1 = Theater.builder()
                .id(1L)
                .title("theater1")
                .build();
        when(theaterService.getByMovieAndDate(eq(1L), Mockito.any(LocalDateTime.class))).thenReturn(Arrays.asList(theater1));
        when(theaterService.findAll()).thenReturn(Arrays.asList(theater1));
        when(theaterService.getOne(1L)).thenReturn(theater1);

        Session session1 = Session.builder()
                .id(1L)
                .price(100)
                .build();
        Map<Long, List<Session>> sessionsPerTheater = new HashMap<>();
        sessionsPerTheater.put(1L, Arrays.asList(session1));
        when(sessionService.getPerTheaterByMovieAndDate(eq(1L), Mockito.any(LocalDateTime.class))).thenReturn(sessionsPerTheater);
        when(sessionService.getPerMovieByTheaterAndDate(eq(1L), Mockito.any(LocalDateTime.class))).thenReturn(sessionsPerTheater);
    }

    @Test
    public void indexShouldReturnListOfMoviesAndRenderIndexView() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("movies", hasSize(1)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("movie1"))
                        )
                )));
    }

    @Test
    public void movieShouldReturnRelatedModelsAndRenderMovieView() throws Exception {
        mockMvc.perform(get("/movie/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("movie"))
                .andExpect(model().attribute("movie", hasProperty("id", is(1L))))
                .andExpect(model().attribute("movie", hasProperty("title", is("movie1"))))
                .andExpect(model().attribute("theaters", hasSize(1)))
                .andExpect(model().attribute("theaters", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("theater1"))
                        )
                )))
                .andExpect(model().attribute("sessions", hasValue(hasSize(1))))
                .andExpect(model().attribute("sessions", hasValue(
                        hasItem(
                                allOf(
                                        hasProperty("id", is(1L)),
                                        hasProperty("price", is(100))
                                )
                        ))))
        ;
    }

    @Test
    public void theatersShouldReturnListOfTheatersAndRenderTheatersView() throws Exception {
        mockMvc.perform(get("/theaters"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("theaters"))
                .andExpect(model().attribute("theaters", hasSize(1)))
                .andExpect(model().attribute("theaters", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("theater1"))
                        )
                )));
    }

    @Test
    public void theaterShouldReturnRelatedModelsAndReturnTheaterView() throws Exception {
        mockMvc.perform(get("/theater/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("theater"))
                .andExpect(model().attribute("theater", hasProperty("id", is(1L))))
                .andExpect(model().attribute("theater", hasProperty("title", is("theater1"))))
                .andExpect(model().attribute("movies", hasSize(1)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("movie1"))
                        )
                )))
                .andExpect(model().attribute("sessions", hasValue(hasSize(1))))
                .andExpect(model().attribute("sessions", hasValue(
                        hasItem(
                                allOf(
                                        hasProperty("id", is(1L)),
                                        hasProperty("price", is(100))
                                )
                        ))))
        ;
    }

}
