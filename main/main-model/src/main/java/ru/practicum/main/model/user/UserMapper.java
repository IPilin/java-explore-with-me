package ru.practicum.main.model.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromDto(UserDto userDto);
}
