package ru.practicum.main.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.UserRequestDto;
import ru.practicum.main.dto.response.UserResponseDto;
import ru.practicum.main.entity.UserEntity;
import ru.practicum.main.mapper.UserMapper;
import ru.practicum.main.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService service;
    private final UserMapper mapper;

    public UserResponseDto create(UserRequestDto dto) {
        UserEntity entity = mapper.buildEntity(dto);
        return mapper.buildDto(service.createUser(entity));
    }

    public void delete(Long id) {
        service.deleteUser(id);
    }

    public List<UserResponseDto> getUsers(List<Long> ids, int from, int size) {
        return mapper.buildDtoList(service.getUsers(ids, from, size));
    }
}
