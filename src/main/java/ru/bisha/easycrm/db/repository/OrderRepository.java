package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
