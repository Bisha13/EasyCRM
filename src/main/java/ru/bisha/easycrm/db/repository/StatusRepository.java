package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
