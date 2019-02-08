package com.publab.theater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "hall")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(name = "title")
    @NotEmpty(message = "*Please provide hall title")
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private Set<Session> sessions;
}

