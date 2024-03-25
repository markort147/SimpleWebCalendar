package webCalendarSpring.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.business.Event;
import webCalendarSpring.business.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        Optional<Event> event = eventService.getById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return new ResponseEntity<>(Map.of("message", "The event doesn't exist!"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        Optional<Event> event = eventService.getById(id);
        if (event.isPresent()) {
            eventService.remove(event.get());
            return ResponseEntity.ok(event.get());
        } else {
            return new ResponseEntity<>(Map.of("message", "The event doesn't exist!"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getEventsByDateRange(
            @RequestParam(name = "start_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateMin,
            @RequestParam(name = "end_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateMax) {
        List<Event> events;
        if (dateMax != null && dateMin != null) {
            events = eventService.getByDateRange(dateMin, dateMax);
        } else {
            events = eventService.getAll();
        }
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayEvents() {
        List<Event> todayEvents = eventService.getTodayEvents();
        return ResponseEntity.ok(todayEvents);
    }

    @PostMapping
    public ResponseEntity<?> postEvent(@RequestBody @Valid Event event, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("");
        } else {
            eventService.save(event);
            return ResponseEntity.ok(
                    Map.of(
                            "message", "The event has been added!",
                            "event", event.getEvent(),
                            "date", event.getDate().toString()
                    )
            );
        }
    }
}
