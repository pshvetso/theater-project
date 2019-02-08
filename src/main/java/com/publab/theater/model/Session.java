package com.publab.theater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "session")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    @ToString.Exclude
    private Hall hall;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id", nullable = false)
    @ToString.Exclude
    private Movie movie;

    @Column(name = "price")
    private int price;

    @OrderColumn
    @Column(name = "time")
    private LocalDateTime time;

    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Seat> seats;
}
