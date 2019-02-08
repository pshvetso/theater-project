package com.publab.theater.service;

import com.publab.theater.model.Seat;
import com.publab.theater.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("bookingService")
public class BookingService {
    private SeatRepository seatRepository;

    @Autowired
    public BookingService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat bookSeat(Seat seat) {
        Seat savedSeat = seatRepository.saveAndFlush(seat);

        return savedSeat;
    }

    public void deleteAll(Set<Seat> seats) {
        seatRepository.deleteAll(seats);
    }
}
