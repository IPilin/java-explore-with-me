package ru.practicum.main.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findAllByInitiator(User initiator, Pageable pageable);

    Optional<Event> findByIdAndInitiatorIs(Long eventId, User initiator);

    Optional<Event> findEventByIdAndStateEquals(Long eventId, EventState state);

    Boolean existsByCategoryId(Long catId);

    List<Event> findAllByIdIn(List<Long> eventId);
}
