package webCalendarSpring.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webCalendarSpring.persistence.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public List<Event> getByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    public List<Event> getByDateRange(LocalDate dateMin, LocalDate dateMax) {
        return eventRepository.findByDateBetween(dateMin, dateMax);
    }

    public List<Event> getTodayEvents() {
        return getByDate(LocalDate.now());
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }

    public void remove(Event event) {
        eventRepository.delete(event);
    }
}
