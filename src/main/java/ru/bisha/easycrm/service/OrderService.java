package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrder(int id);

    Order saveOrder(Order order);

    Page<Order> getPageOfOrders(PageRequest request);
}
