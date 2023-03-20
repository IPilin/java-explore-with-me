package ru.practicum.main.service.user;

import ru.practicum.main.model.user.User;
import ru.practicum.main.model.user.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getAll(List<Long> ids, Integer from, Integer size);

    User create(UserDto userDto);

    void remove(Long userId);
}
