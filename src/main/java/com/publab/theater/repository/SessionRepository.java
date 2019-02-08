package com.publab.theater.repository;

import com.publab.theater.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    /*@Query("SELECT DISTINCT s " +
            "FROM Session s " +
            "JOIN s.hall h " +
            "JOIN h.theater t " +
            "WHERE t.id = :theater " +
            "ORDER BY s.time")
    List<Session> getByTheater(@Param("theater") Long theater);*/

    /*@Query("SELECT DISTINCT s " +
            "FROM Session s " +
            "JOIN s.hall h " +
            "JOIN h.theater t " +
            "JOIN s.movie m " +
            "WHERE t.id = :theater AND m.id = :movie AND DATE(s.time) = DATE(:date) " +
            "ORDER BY s.time")
    List<Session> getByTheaterAndMovie(@Param("theater") Long theater, @Param("movie") Long movie, @Param("date") Date date);*/

    @Query("SELECT DISTINCT s " +
            "FROM Session s " +
            "JOIN s.movie m " +
//            "WHERE m.id = :movie " +
//            "WHERE m.id = :movie AND SUBSTRING(s.time, 1,10) = SUBSTRING(:date, 1,10) " +
//            "WHERE m.id = :movie AND DATE_TRUNC('day',s.time) = DATE_TRUNC('day',:date) " +
//            "WHERE m.id = :movie AND DATEDIFF(s.time, :date) = 0 " +
//            "WHERE m.id = :movie AND DATE(s.time) = DATE(:date) " +
            "WHERE m.id = :movie AND CAST(s.time AS date) = CAST(:date AS date) " +
            "ORDER BY s.time")
    List<Session> getByMovieAndDate(@Param("movie") Long movie, @Param("date") LocalDateTime date);

    @Query("SELECT DISTINCT s " +
            "FROM Session s " +
            "JOIN s.hall h " +
            "JOIN h.theater t " +
            "WHERE t.id = :theater AND CAST(s.time AS date) = CAST(:date AS date) " +
            "ORDER BY s.time")
    List<Session> getByTheaterAndDate(@Param("theater") Long theater, @Param("date") LocalDateTime date);

    //List<Session> findByMovieIdAndTimeOrderByTime(Long theaterId, LocalDateTime date);
}
