package ru.bisha.easycrm.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bisha.easycrm.db.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

    List<OrderEntity> findAllByClientId(int id);

    @Query(value = "select o from OrderEntity o where o.device.deviceName like lower(:search)")
    List<OrderEntity> findByString(@Param("search") String search);

    Page<OrderEntity> findAllByExecuteStatusId (long id, Pageable pageable);

    Page<OrderEntity> findAllByExecuteStatusHide(boolean hide, Pageable pageable);
}
