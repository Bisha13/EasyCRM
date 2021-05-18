package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.entity.User;
import ru.bisha.easycrm.service.OrderService;
import ru.bisha.easycrm.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {


    private final OrderService orderService;

    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping
    public String getAllOrders(Model model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderListAttr", orderList);
        return "allOrders";
    }

    @RequestMapping("/{id}")
    public String getOrder(@PathVariable int id, Model model) {
        Order order = orderService.getOrder(id);
        List<User> users = userService.getAllUsers();
        model.addAttribute("orderAtr", order);
        model.addAttribute("usersAtr", users);
        return "order";
    }


}

