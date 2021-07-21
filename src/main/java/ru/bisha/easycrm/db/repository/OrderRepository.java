package ru.bisha.easycrm.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bisha.easycrm.db.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findAllByClientId(int id);

    @Query(value = "select o from Order o where o.device.deviceName like lower(:search) or " +
            "o.device.vendor like lower(:search) or o.device.model like lower(:search)")
    List<Order> findByString(@Param("search") String search);

    Page<Order> findAllByExecuteStatusId (long id, Pageable pageable);

    Page<Order> findAllByExecuteStatusIdNot(long id, Pageable pageable);
}
