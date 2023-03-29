package ru.practicum.main.service.request;

import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.model.request.dto.UpdateRequestDto;
import ru.practicum.main.model.request.model.Request;

import java.util.Collection;
import java.util.Map;

public interface RequestService {
    Collection<Request> getAll(Long userId);

    Request create(Long userId, Long eventId);

    Request cancel(Long userId, Long requestId);

    Collection<Request> getAllInEvent(Long userId, Long eventId);

    Map<String, Collection<RequestDto>> updateRequests(Long userId, Long eventId, UpdateRequestDto updateRequestDto);
}
