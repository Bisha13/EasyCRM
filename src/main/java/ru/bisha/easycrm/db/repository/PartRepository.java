package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bisha.easycrm.db.entity.Part;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> getAllByStockId(int id);
}
