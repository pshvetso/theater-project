package com.publab.theater.repository;

import com.publab.theater.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // movie <- session(income) -> hall -> theater
    @Query("SELECT DISTINCT m " +
            "FROM Movie m " +
            "JOIN m.sessions s " +
            "JOIN s.hall h " +
            "JOIN h.theater t " +
//            "WHERE t.id = :theater AND DATE(s.time) = DATE(:date)")
            "WHERE t.id = :theater AND CAST(s.time AS date) = CAST(:date AS date)")
    List<Movie> getByTheaterAndDate(@Param("theater") Long theater, @Param("date") LocalDateTime date);
}
