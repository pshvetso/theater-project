package com.publab.theater.controller;

import com.publab.theater.TheaterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TheaterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class BookingRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void bookShouldReturnBookedSeatObject() throws Exception {
        mockMvc.perform(get("/book?session={id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.booked", is(true)));
    }

    @Test
    public void movieSessionsShouldReturnListOfSessionsForMovieOnDate() throws Exception {
        mockMvc.perform(get("/movie-sessions?id={id}&date={date}", 1, "2018-11-09")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.1", hasSize(6)))
                .andExpect(jsonPath("$.1[*].id", containsInAnyOrder(21, 22, 23, 24, 26, 31)))
                .andExpect(jsonPath("$.1[*].time", containsInAnyOrder(
                        "2018-11-09T10:15:00", "2018-11-09T12:55:00", "2018-11-09T18:25:00",
                        "2018-11-09T21:10:00", "2018-11-09T15:40:00", "2018-11-09T23:55:00"
                )));
    }

    @Test
    public void theaterSessionsShouldReturnListOfSessionsInTheaterOnDate() throws Exception {
        mockMvc.perform(get("/theater-sessions?id={id}&date={date}", 1, "2018-11-09")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.1", hasSize(6)))
                .andExpect(jsonPath("$.1[*].id", containsInAnyOrder(21, 22, 23, 24, 26, 31)))
                .andExpect(jsonPath("$.1[*].time", containsInAnyOrder(
                        "2018-11-09T10:15:00", "2018-11-09T12:55:00", "2018-11-09T18:25:00",
                        "2018-11-09T21:10:00", "2018-11-09T15:40:00", "2018-11-09T23:55:00"
                )));
    }
}
