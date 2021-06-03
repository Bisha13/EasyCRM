package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Integer> {
}
