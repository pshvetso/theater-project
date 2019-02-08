package com.publab.theater.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


@Entity
@Table(name = "theater")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private long id;

    @Column(name = "title")
    @NotEmpty(message = "*Please provide theater title")
    private String title;

    @Column(name = "address")
    @NotEmpty(message = "*Please provide theater address")
    private String address;

    @Column(name = "descr")
    @NotEmpty(message = "*Please provide theater description")
    private String descr;

    @Column(name = "image")
    @NotEmpty(message = "*Please provide theater image")
    private String image;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Hall> halls;
}
