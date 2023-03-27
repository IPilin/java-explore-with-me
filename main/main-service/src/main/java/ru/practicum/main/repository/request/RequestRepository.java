package ru.practicum.main.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.request.model.Request;
import ru.practicum.main.model.request.model.RequestState;
import ru.practicum.main.model.user.User;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterIs(User requester);

    Boolean existsByRequesterAndEvent(User requester, Event event);

    Optional<Request> findByIdAndRequester(Long requestId, User requester);

    List<Request> findAllByEventInAndStatusIs(List<Event> events, RequestState state);

    List<Request> findAllByEvent(Event event);

    List<Request> findAllByEventAndIdIn(Event event, List<Long> ids);
}
