package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
