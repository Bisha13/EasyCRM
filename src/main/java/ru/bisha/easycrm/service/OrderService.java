package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    List<Order> getOrdersByClientId(int id);

    Order getOrder(int id);

    Order saveOrder(Order order);

    Page<Order> getPageOfOrders(PageRequest request);

    Page<Order> getByStatusId(long id, PageRequest request);

    Page<Order> getByStatusIdNot(long id, PageRequest request);

    List<Order> getFiltered(String search);
}
