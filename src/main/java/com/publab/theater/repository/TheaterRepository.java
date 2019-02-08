package com.publab.theater.repository;

import com.publab.theater.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    /*
    movie <- session(income) -> hall -> theater

    SELECT t.*
    FROM theater t
    JOIN hall h ON t.theater_id = h.theater_id
    JOIN session s ON s.hall_id = h.hall_id
    JOIN movie m ON s.movie_id = m.movie_id
    WHERE m.movie_id = 5
     */
    @Query("SELECT DISTINCT t " +
            "FROM Theater t " +
            "JOIN t.halls h " +
            "JOIN h.sessions s " +
            "JOIN s.movie m " +
//            "WHERE m.id = :movie AND m.id = :movie AND DATE(s.time) = DATE(:date)")
            "WHERE m.id = :movie AND m.id = :movie AND CAST(s.time AS date) = CAST(:date AS date)")
    List<Theater> getByMovie(@Param("movie") Long movie, @Param("date") LocalDateTime date);
}
