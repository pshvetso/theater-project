package com.publab.theater.repository;

import com.publab.theater.model.Theater;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TheaterRepositoryUnitTest {

    @Autowired
    private TheaterRepository theaterRepository;

    @Test
    public void getByMovieShouldFindTheatersByMovie() {
        List<Theater> foundTheaters = theaterRepository.getByMovie(1L, LocalDateTime.parse("2018-11-09T10:15:00"));
        assertThat(foundTheaters)
                .hasSize(2)
                .extracting(Theater::getId)
                .containsExactlyInAnyOrder(1L, 2L);
        assertThat(foundTheaters)
                .extracting(Theater::getTitle)
                .containsExactlyInAnyOrder("Синема Парк Фантастика", "Кинотеатр Синема Парк Седьмое небо");
    }
}
