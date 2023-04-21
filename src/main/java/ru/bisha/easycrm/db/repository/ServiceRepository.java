package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.bisha.easycrm.db.entity.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> getAllByItemId(int id);

    @Modifying
    @Query("delete from Service s where s.serviceId = ?1")
    @Transactional
    void delete(Integer serviceId);

    @Modifying
    @Query("DELETE FROM Service s WHERE s.serviceId in ?1")
    @Transactional
    void delete(Set<Integer> ids);
}
