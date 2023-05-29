package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.WorkEntity;

public interface WorkRepository extends JpaRepository<WorkEntity, Integer> {
}
