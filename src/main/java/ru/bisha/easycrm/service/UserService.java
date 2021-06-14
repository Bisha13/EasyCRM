package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
}
