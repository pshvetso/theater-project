package com.publab.theater.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "movie")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private long id;

    @Column(name = "title")
    @NotEmpty(message = "*Please provide title")
    private String title;

    @Column(name = "descr")
    @NotEmpty(message = "*Please provide description")
    private String descr;

    @Column(name = "image")
    @NotEmpty(message = "*Please provide image")
    private String image;

    @Column(name = "genre")
    @NotEmpty(message = "*Please provide genre")
    private String genre;

    @Column(name = "time")
    private int time;

    @Column(name = "release_")
    @NotEmpty(message = "*Please provide release")
    private String release;

    @Column(name = "rating")
    @NotEmpty(message = "*Please provide rating")
    private String rating;

    @Column(name = "studio")
    @NotEmpty(message = "*Please provide studio")
    private String studio;

    @Column(name = "producer")
    @NotEmpty(message = "*Please provide producer")
    private String producer;

    @Column(name = "director")
    @NotEmpty(message = "*Please provide director")
    private String director;

    @Column(name = "actors")
    @NotEmpty(message = "*Please provide actors")
    private String actors;

    @Column(name = "country")
    @NotEmpty(message = "*Please provide country")
    private String country;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Session> sessions;
}
