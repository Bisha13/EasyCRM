package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrder(int id);

    void saveOrder(Order order);
}
