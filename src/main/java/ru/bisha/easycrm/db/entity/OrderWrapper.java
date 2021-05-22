package ru.bisha.easycrm.db.entity;

import java.util.ArrayList;
import java.util.List;

public class OrderWrapper {

    private List<Order> orderList;

    public void addOrder(Order order) {
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        orderList.add(order);
    }
}
