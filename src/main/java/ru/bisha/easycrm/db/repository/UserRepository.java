package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
