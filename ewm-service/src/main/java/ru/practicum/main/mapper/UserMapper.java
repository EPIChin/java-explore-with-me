package ru.practicum.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.UserRequestDto;
import ru.practicum.main.dto.response.UserResponseDto;
import ru.practicum.main.dto.response.UserResponseShortDto;
import ru.practicum.main.entity.UserEntity;

import java.util.List;

@Component
public class UserMapper {

    public UserEntity buildEntity(UserRequestDto dto) {
        return UserEntity.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .build();
    }

    public UserResponseDto buildDto(UserEntity entity) {
        return UserResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .build();
    }

    public List<UserResponseDto> buildDtoList(List<UserEntity> entities) {
        return entities.stream().map(this::buildDto).toList();
    }

    public UserResponseShortDto buildShortDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserResponseShortDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }
}
