package ru.bisha.easycrm.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderWrapper {

    private List<Order> orderList;

    private Client client;

    public void addOrder(Order order) {
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        orderList.add(order);
    }

    public OrderWrapper() {
        this.client = new Client();
    }
}
