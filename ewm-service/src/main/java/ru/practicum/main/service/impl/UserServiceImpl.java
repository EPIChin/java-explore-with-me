package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.entity.UserEntity;
import ru.practicum.main.exception.AlreadyExists;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.repository.UserRepository;
import ru.practicum.main.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserEntity createUser(UserEntity entity) {
        validateEmailUnique(entity.getEmail());
        return repository.save(entity);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity existing = getUserOrThrow(id);
        repository.delete(existing);
    }

    @Override
    public List<UserEntity> getUsers(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        if (ids == null || ids.isEmpty()) {
            return repository.findAll(pageable).getContent();
        }
        return repository.findByIdIn(ids, pageable).getContent();
    }

    @Override
    public UserEntity getById(Long id) {
        return getUserOrThrow(id);
    }

    private UserEntity getUserOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
    }

    private void validateEmailUnique(String email) {
        if (email == null) {
            return;
        }
        if (repository.existsByEmail(email)) {
            throw new AlreadyExists("Пользователь с email='" + email + "' уже существует");
        }
    }
}
