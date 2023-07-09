package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer> {
}
