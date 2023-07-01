package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.bisha.easycrm.db.entity.ServiceEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    List<ServiceEntity> getAllByItemId(int id);

    @Modifying
    @Query("delete from ServiceEntity s where s.serviceId = ?1")
    @Transactional
    void delete(Integer serviceId);

    @Modifying
    @Query("DELETE FROM ServiceEntity s WHERE s.serviceId in ?1")
    @Transactional
    void delete(Set<Integer> ids);

    @Transactional
    @Query(value = "UPDATE services s set s.status = :status, s.status_updated_at = now() WHERE s.service_id IN :ids", nativeQuery = true)
    @Modifying
    void updateStatuses(List<Integer> ids, String status);
}
