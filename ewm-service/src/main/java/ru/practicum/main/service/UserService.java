package ru.practicum.main.service;

import ru.practicum.main.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity entity);

    void deleteUser(Long id);

    List<UserEntity> getUsers(List<Long> ids, int from, int size);

    UserEntity getById(Long id);
}
