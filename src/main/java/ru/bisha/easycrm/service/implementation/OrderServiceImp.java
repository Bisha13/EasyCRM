package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.entity.Part;
import ru.bisha.easycrm.db.entity.Service;
import ru.bisha.easycrm.db.entity.Stock;
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
        setSumFromServices(order);

        setSumFromParts(order);
        var parts = order.getListOfParts();
        setOrders(order);
        if (parts.size() == 1 &&
                parts.get(0).getName() == null && !parts.get(0).getIsStock()) {
            order.setListOfParts(null);
        }
        order.setFullPrice(order.getPartsPrice() + order.getWorkPrice());
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getPageOfOrders(PageRequest request) {
        return orderRepository.findAll(request);
    }

    @Override
    public Page<Order> getByStatusIdNot(long id, PageRequest request) {
        return orderRepository.findAllByExecuteStatusIdNot(id, request);
    }

    @Override
    public Page<Order> getByStatusId(long id, PageRequest request) {
        return orderRepository.findAllByExecuteStatusId(id, request);
    }

    @Override
    public List<Order> getFiltered(String search) {
        search = "%" + search.toLowerCase() + "%";
        return orderRepository.findByString(search);
    }

    private double applyDiscount(double sum, Integer discount) {
        if (discount == null) {
            return sum;
        }
        return sum - (sum / 100 * discount);
    }

    private void setSumFromParts(Order order) {
        final List<Part> listOfParts = order.getListOfParts();
        Double partsPrice = listOfParts.stream()
                .mapToDouble(p -> p.getPrice() * p.getQty())
                .sum();
        Double stockPrice = listOfParts.stream()
                .filter(part -> part.getIsStock())
                .mapToDouble(p -> p.getStock().getPrice() * p.getQty())
                .sum();

        order.setPartsPrice(partsPrice + stockPrice);

    }

    private void setSumFromServices(Order order) {
        var services = order.getListOfServices();
        var sum = 0.0;
        for (Service service : services) {
            Double price = 0.0;
            if (service.getIsCustom()) {
                price = service.getPrice();
            }
            if (!service.getIsCustom()) {
                price = service.getItem().getPrice();
            }
            price = price * service.getQty();
            price = applyDiscount(price, order.getClient().getDiscount());

            int percent;
            if (service.getExecutor() == null) {
                percent = 0;
            } else {
                percent = service.getExecutor().getPercent();
            }

            double executorMoney = (price / 100.0) * percent;
            service.setExecutorMoney(executorMoney);
            service.setProfit(price - executorMoney);
            sum += price;
        }
        order.setWorkPrice(sum);
    }

    private void setOrders(Order order) {
        for (Part part : order.getListOfParts()) {
            part.setOrder(order);
        }
        for (Service service : order.getListOfServices()) {
            service.setOrder(order);
        }
    }
}
