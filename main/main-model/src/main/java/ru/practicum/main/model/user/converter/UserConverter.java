package ru.practicum.main.model.user.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.main.model.user.User;
import ru.practicum.main.model.user.UserDto;

@Component
public class UserConverter {
    private final ModelMapper mapper;

    public UserConverter() {
        mapper = new ModelMapper();
    }

    public User fromDto(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }
}
