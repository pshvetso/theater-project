package com.publab.theater.controller;

import com.publab.theater.model.Seat;
import com.publab.theater.model.Session;
import com.publab.theater.service.BookingService;
import com.publab.theater.service.SessionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingRestController.class)
public class BookingRestControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private SessionService sessionService;

    @Before
    public void setUp() {
        Session session1 = Session.builder()
                .id(1L)
                .price(100)
                .time(LocalDateTime.parse("2018-11-09T10:15:00"))
                .seats(new HashSet<>())
                .build();
        given(sessionService.getOne(1L)).willReturn(session1);

        Seat seat1 = Seat.builder()
                .id(1L)
                .booked(true)
                .build();
        given(bookingService.bookSeat(any(Seat.class))).willReturn(seat1);

        Map<Long, List<Session>> sessionsPerTheater = new HashMap<>();
        sessionsPerTheater.put(1L, Arrays.asList(session1));
        given(sessionService.getPerTheaterByMovieAndDate(eq(1L), any(LocalDateTime.class))).willReturn(sessionsPerTheater);
        given(sessionService.getPerMovieByTheaterAndDate(eq(1L), any(LocalDateTime.class))).willReturn(sessionsPerTheater);

    }

    @Test
    public void bookShouldBookSeatAndReturnSeatObject() throws Exception {
        mockMvc.perform(get("/book?session={id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.booked", is(true)));
    }

    @Test
    public void movieSessionsShouldReturnListOfSessionsForMovieOnDate() throws Exception {
        mockMvc.perform(get("/movie-sessions?id={id}&date={date}", 1, "2018-11-09")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1", hasSize(1)))
                .andExpect(jsonPath("$.1[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$.1[*].time", containsInAnyOrder("2018-11-09T10:15:00")));
    }

    @Test
    public void theaterSessionsShouldReturnListOfSessionsInTheaterOnDate() throws Exception {
        mockMvc.perform(get("/theater-sessions?id={id}&date={date}", 1, "2018-11-09")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1", hasSize(1)))
                .andExpect(jsonPath("$.1[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$.1[*].time", containsInAnyOrder("2018-11-09T10:15:00")));
    }
}
