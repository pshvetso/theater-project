package com.publab.theater.controller;

import com.publab.theater.TheaterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TheaterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class IndexControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnMoviesListAndRenderIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("movies", hasSize(7)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("Репродукция"))
                        ))
                ))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("title", is("Несокрушимый"))
                        ))
                ));
    }

    @Test
    public void movieShouldReturnRelatedModelsAndRenderMovieView() throws Exception {
        mockMvc.perform(get("/movie/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("movie"))
                .andExpect(model().attribute("movie", hasProperty("id", is(1L))))
                .andExpect(model().attribute("movie", hasProperty("title", is("Репродукция"))))
                .andExpect(model().attribute("theaters", hasSize(2)))
                .andExpect(model().attribute("theaters", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("Синема Парк Фантастика"))
                        )
                )))
                .andExpect(model().attribute("theaters", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("title", is("Кинотеатр Синема Парк Седьмое небо"))
                        )
                )))
                .andExpect(model().attribute("sessions", hasValue(hasSize(6))))
                .andExpect(model().attribute("sessions", hasValue(
                        hasItem(
                                allOf(
                                        hasProperty("id", is(21L)),
                                        hasProperty("price", is(140))
                                )
                        ))))
                .andExpect(model().attribute("sessions", hasValue(hasSize(5))))
                .andExpect(model().attribute("sessions", hasValue(
                        hasItem(
                                allOf(
                                        hasProperty("id", is(27L)),
                                        hasProperty("price", is(140))
                                )
                        ))));
    }

    @Test
    public void theatersShouldReturnMoviesListAndRenderTheatersView() throws Exception {
        mockMvc.perform(get("/theaters"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("theaters"))
                .andExpect(model().attribute("theaters", hasSize(2)))
                .andExpect(model().attribute("theaters", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("Синема Парк Фантастика"))
                        )
                )))
                .andExpect(model().attribute("theaters", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("title", is("Кинотеатр Синема Парк Седьмое небо"))
                        )
                )));
    }

    @Test
    public void theaterShouldReturnRelatedModelsAndRenderTheaterView() throws Exception {
        mockMvc.perform(get("/theater/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("theater"))
                .andExpect(model().attribute("theater", hasProperty("id", is(1L))))
                .andExpect(model().attribute("theater", hasProperty("title", is("Синема Парк Фантастика"))))
                .andExpect(model().attribute("movies", hasSize(4)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("Репродукция"))
                        )
                )))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("title", is("Несокрушимый"))
                        ))
                ))
                .andExpect(model().attribute("sessions", hasValue(hasSize(6))))
                .andExpect(model().attribute("sessions", hasValue(
                        hasItem(
                                allOf(
                                        hasProperty("id", is(21L)),
                                        hasProperty("price", is(140))
                                )
                        ))));
    }
}
