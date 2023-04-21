package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bisha.easycrm.db.entity.Part;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> getAllByStockId(int id);

    @Modifying
    @Query("delete from Part p where p.partId = ?1")
    @Transactional
    void delete(Integer partId);

    @Modifying
    @Query("DELETE FROM Part p WHERE p.partId in ?1")
    @Transactional
    void delete(Set<Integer> ids);
}
