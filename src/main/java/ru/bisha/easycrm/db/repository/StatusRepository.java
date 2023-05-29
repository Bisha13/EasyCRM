package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
