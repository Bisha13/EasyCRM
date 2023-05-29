package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getById(int id);
    void save(UserEntity user);
    void delete(int id);
}
