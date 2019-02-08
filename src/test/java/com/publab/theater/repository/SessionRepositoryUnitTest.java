package com.publab.theater.repository;

import com.publab.theater.model.Session;
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
public class SessionRepositoryUnitTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void getByMovieAndDateShouldFindSessionsByMovieAndDate() {
        List<Session> foundSessions = sessionRepository.getByMovieAndDate(1L, LocalDateTime.parse("2018-11-09T10:15:00"));
        assertThat(foundSessions)
                .hasSize(11)
                .extracting(Session::getId)
                .containsExactlyInAnyOrder(27L, 21L, 28L, 22L, 26L, 23L, 29L, 30L, 24L, 31L, 25L);
    }

    @Test
    public void getByTheaterAndDateShouldFindSessionsByTheaterAndDate() {
        List<Session> foundSessions = sessionRepository.getByTheaterAndDate(1L, LocalDateTime.parse("2018-11-09T10:15:00"));
        assertThat(foundSessions)
                .hasSize(23)
                .extracting(Session::getId)
                .containsExactlyInAnyOrder(41L, 21L, 61L, 7L, 63L, 8L, 43L, 22L, 65L, 45L, 26L, 66L, 9L, 47L, 23L, 68L, 10L, 49L, 24L, 31L, 70L, 11L, 51L);
    }
}
