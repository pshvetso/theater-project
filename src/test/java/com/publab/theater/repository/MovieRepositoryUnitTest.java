package com.publab.theater.repository;

import com.publab.theater.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class MovieRepositoryUnitTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void getByTheaterAndDateShouldFindSessionsByTheaterAndDate() {
        List<Movie> foundMovies = movieRepository.getByTheaterAndDate(1L, LocalDateTime.parse("2018-11-09T10:15:00"));
        assertEquals(foundMovies.size(), 4);
        assertThat(foundMovies)
                .hasSize(4)
                .extracting(Movie::getId)
                .containsExactlyInAnyOrder(1L, 2L, 3L, 5L);
        assertThat(foundMovies)
                .extracting(Movie::getTitle)
                .containsExactlyInAnyOrder("Богемская рапсодия ",
                        "Репродукция",
                        "Ужастики 2: Беспокойный Хеллоуин",
                        "Несокрушимый");
    }
}
