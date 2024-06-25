package com.markort147.simplecalendarwebapi.persistence;

import com.markort147.simplecalendarwebapi.business.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDate(LocalDate date);

    List<Event> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
