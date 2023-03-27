package ru.practicum.main.admin.controller.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.request.RequestListMapper;
import ru.practicum.main.model.request.RequestMapper;
import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.service.request.RequestService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService service;
    private final RequestMapper mapper;
    private final RequestListMapper listMapper;

    @GetMapping
    public Collection<RequestDto> getAllRequests(@PathVariable Long userId) {
        return listMapper.toDto(service.getAll(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable Long userId,
                                    @RequestParam Long eventId) {
        return mapper.toDto(service.create(userId, eventId));
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) {
        return mapper.toDto(service.cancel(userId, requestId));
    }
}
