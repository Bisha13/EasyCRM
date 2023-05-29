package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.UserEntity;
import ru.bisha.easycrm.db.repository.UserRepository;
import ru.bisha.easycrm.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> getById(int id) {
        return userRepository.findById(id);
    }
}
