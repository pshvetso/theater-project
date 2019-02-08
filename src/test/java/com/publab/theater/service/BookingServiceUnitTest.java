package com.publab.theater.service;

import com.publab.theater.model.Seat;
import com.publab.theater.repository.SeatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceUnitTest {

    @MockBean
    private SeatRepository seatRepository;

    @Autowired
    private BookingService bookingService;

    @Test
    public void bookSeatShouldSaveSeatAndReturn() {
        Seat seat1 = Seat.builder()
                .id(1L)
                .booked(true)
                .build();
        given(seatRepository.saveAndFlush(any())).willReturn(seat1);
        Seat savedSeat = bookingService.bookSeat(seat1);
        assertThat(savedSeat).isEqualTo(seat1);
    }
}
