package ru.bisha.easycrm.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderWrapper {

    private List<OrderEntity> orderList;

    private ClientEnitiy client;

    public void addOrder(OrderEntity order) {
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        orderList.add(order);
    }

    public OrderWrapper() {
        this.client = new ClientEnitiy();
    }
}
