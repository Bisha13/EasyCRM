package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.entity.Service;
import ru.bisha.easycrm.db.repository.OrderRepository;
import ru.bisha.easycrm.service.OrderService;

import java.util.List;

@org.springframework.stereotype.Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by("orderId").descending());
    }

    @Override
    public List<Order> getOrdersByClientId(int id) {
        return orderRepository.findAllByClientId(id);
    }

    public Order getOrder(int id) {
        return orderRepository.findById(id)
                .orElseGet(Order::new);
    }

    @Override
    public Order saveOrder(Order order) {
        List<Service> list = order.getListOfServices();
        var sum = 0.0;
        for (Service service : list) {
            if (service.getExecutorMoney() != null
                        || service.getProfit() != null) {
                continue;
            }
            Double price = 0.0;
            if (!service.getDescription().isEmpty()) {
                price = service.getPrice();
            }
            if (service.getDescription().isEmpty()) {
                price = service.getItem().getPrice();
            }
            price = price * service.getQty();
            price = applyDiscount(price, order.getClient().getDiscount());
            double executorMoney = (price / 100.0)
                    * service.getExecutor().getPercent();
            service.setExecutorMoney(executorMoney);
            service.setProfit(price - executorMoney);
            sum += price;
        }

        order.setWorkPrice(sum);

        return orderRepository.save(order);
    }

    private double applyDiscount(double sum, Integer discount) {
        if (discount == null) {
            return sum;
        }
        return sum - (sum / 100 * discount);
    }

    @Override
    public Page<Order> getPageOfOrders(PageRequest request) {
        return orderRepository.findAll(request);
    }
}
