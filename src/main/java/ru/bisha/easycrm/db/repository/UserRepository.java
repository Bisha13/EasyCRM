package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
