package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getById(int id);
    void save(User user);
    void delete(int id);
}
