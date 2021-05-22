package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bisha.easycrm.db.entity.*;
import ru.bisha.easycrm.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {


    private final OrderService orderService;

    private final UserService userService;

    private final ClientService clientService;

    private final DeviceService deviceService;

    private final ItemService itemService;


    @RequestMapping
    public String getAllOrders(Model model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderListAttr", orderList);
        return "allOrders";
    }

    @RequestMapping("/{id}")
    public String getOrder(@PathVariable int id, Model model) {
        Order order = orderService.getOrder(id);
        Client client = clientService.getClient(order.getClientId());
        Device device = deviceService.getDevice(order.getDeviceId());
        List<User> users = userService.getAllUsers();
        model.addAttribute("orderAtr", order);
        model.addAttribute("clientAtr", client);
        model.addAttribute("deviceAtr", device);
        model.addAttribute("usersAtr", users);
        return "order";
    }

    @RequestMapping("/save")
    public String saveOrder(@ModelAttribute("orderAtr") final Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping("/new")
    public String newOrder(Model model) {
        OrderWrapper orderWrapper = new OrderWrapper();
        orderWrapper.addOrder(new Order());
        List<Item> itemList = itemService.getAll();

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("ordersAtr", orderWrapper);

        return "newOrder";
    }
}

