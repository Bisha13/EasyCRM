package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.entity.Part;
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
        var services = order.getListOfServices();
        setSumFromServices(order, services);

        var parts = order.getListOfParts();
        setSumFromParts(order, parts);
        if (parts.size() == 1 && parts.get(0).getName().isEmpty()) {
            order.setListOfParts(null);
        }

        parts.forEach(p -> p.setOrder(order));

        return orderRepository.save(order);
    }

    private void setSumFromServices(Order order, List<Service> list) {
        var sum = 0.0;
        for (Service service : list) {
            if (service.getExecutorMoney() != null
                        || service.getProfit() != null
                                || service.getExecutor() == null) {
                continue;
            }
            Double price = 0.0;
            if (!service.getDescription().isEmpty()) {
                price = service.getPrice();
            }
            if (service.getDescription().isEmpty() && service.getPrice() != null) {
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
    }

    private void setSumFromParts(Order order, List<Part> list) {
        var sum = 0.0;
        var purchasePrice = 0.0;
        for (Part part : list) {
            sum += part.getPrice();
            if (part.getPurchasePrice() != null) {
                purchasePrice += part.getPurchasePrice();
            }
        }
        var discount = order.getClient().getDiscount();
        purchasePrice += applyDiscount(sum - purchasePrice, discount);
        order.setPartsPrice(purchasePrice);
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

    @Override
    public List<Order> getFiltered(String search) {
        search = "%" + search.toLowerCase() + "%";
        return orderRepository.findByString(search);
    }
}
