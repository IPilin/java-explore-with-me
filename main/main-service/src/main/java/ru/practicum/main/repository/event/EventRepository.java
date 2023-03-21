package ru.practicum.main.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.model.event.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
