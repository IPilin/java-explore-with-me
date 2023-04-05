package ru.practicum.main.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.model.exception.ConflictException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.model.user.User;
import ru.practicum.main.model.user.UserDto;
import ru.practicum.main.model.user.UserMapper;
import ru.practicum.main.repository.user.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Collection<User> getAll(List<Long> ids, Integer from, Integer size) {
        return userRepository.findAllByIdIn(ids, PageRequest.of(from / size, size));
    }

    @Transactional(readOnly = true)
    @Override
    public User get(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
    }

    @Transactional
    @Override
    public User create(UserDto userDto) {
        try {
            return userRepository.save(mapper.fromDto(userDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void remove(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("User with id=%d was not found", userId));
        }
    }
}
