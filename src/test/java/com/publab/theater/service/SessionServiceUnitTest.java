package com.publab.theater.service;

import com.publab.theater.model.Hall;
import com.publab.theater.model.Movie;
import com.publab.theater.model.Session;
import com.publab.theater.model.Theater;
import com.publab.theater.repository.SessionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionServiceUnitTest {

    @MockBean
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @Test
    public void getPerTheaterByMovieAndDateShouldReturnMapOfSessions() {
        Theater theater1 = Theater.builder()
                .id(1L)
                .title("theater1")
                .build();
        Hall hall1 = Hall.builder()
                .id(1L)
                .title("hall1")
                .theater(theater1)
                .build();
        Session session1 = Session.builder()
                .id(1L)
                .price(100)
                .time(LocalDateTime.parse("2018-11-09T10:15:00"))
                .hall(hall1)
                .build(),
                session2 = Session.builder()
                        .id(2L)
                        .price(200)
                        .time(LocalDateTime.parse("2018-11-09T10:15:00"))
                        .hall(hall1)
                        .build();
        List<Session> sessions = Arrays.asList(session1, session2);

        given(sessionRepository.getByMovieAndDate(any(), any())).willReturn(sessions);
        Map<Long, List<Session>> foundSessions = sessionService.getPerTheaterByMovieAndDate(1L, LocalDateTime.parse("2018-11-09T10:15:00"));
        assertThat(foundSessions.keySet().size()).isEqualTo(1);
        assertThat(foundSessions.get(1L).size()).isEqualTo(2);
        assertThat(foundSessions.get(1L)).contains(session1, session2);
    }

    @Test
    public void getPerMovieByTheaterAndDateShouldReturnMapOfSessions() {
        Movie movie1 = Movie.builder()
                .id(1L)
                .title("movie1")
                .build();
        Session session1 = Session.builder()
                .id(1L)
                .price(100)
                .time(LocalDateTime.parse("2018-11-09T10:15:00"))
                .movie(movie1)
                .build(),
                session2 = Session.builder()
                        .id(2L)
                        .price(200)
                        .time(LocalDateTime.parse("2018-11-09T10:15:00"))
                        .movie(movie1)
                        .build();
        List<Session> sessions = Arrays.asList(session1, session2);

        given(sessionRepository.getByTheaterAndDate(any(), any())).willReturn(sessions);
        Map<Long, List<Session>> foundSessions = sessionService.getPerMovieByTheaterAndDate(1L, LocalDateTime.parse("2018-11-09T10:15:00"));
        assertThat(foundSessions.keySet().size()).isEqualTo(1);
        assertThat(foundSessions.get(1L).size()).isEqualTo(2);
        assertThat(foundSessions.get(1L)).contains(session1, session2);
    }
}
