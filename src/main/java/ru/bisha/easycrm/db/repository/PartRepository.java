package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bisha.easycrm.db.entity.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
}
