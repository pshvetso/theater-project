package com.publab.theater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seat")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(name = "booked")
    private boolean booked;

    public Seat(Session session) {
        this.session = session;
        this.booked = true;
    }
}
