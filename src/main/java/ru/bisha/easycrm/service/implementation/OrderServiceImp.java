package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.repository.OrderRepository;
import ru.bisha.easycrm.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by("orderId").descending());
    }

    public Order getOrder(int id) {
        return orderRepository.findById(id)
                .orElseGet(Order::new);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
