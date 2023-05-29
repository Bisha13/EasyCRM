package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    List<OrderEntity> getAllOrders();

    List<OrderEntity> getOrdersByClientId(int id);

    OrderEntity getOrder(int id);

    OrderEntity saveOrder(OrderEntity order);

    Page<OrderEntity> getPageOfOrders(PageRequest request);

    Page<OrderEntity> getByStatusId(long id, PageRequest request);

    Page<OrderEntity> getAllNotHidden(PageRequest request);

    List<OrderEntity> getFiltered(String search);
}
