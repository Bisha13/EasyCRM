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
        List<User> users = userService.getAllUsers();
        List<Item> items = itemService.getAll();
        model.addAttribute("orderAtr", order);
        model.addAttribute("usersAtr", users);
        model.addAttribute("itemsAtr", items);
        return "order";
    }

    @RequestMapping("/saveWrapper")
    public String saveOrder(@ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper,
                            @ModelAttribute("orderAtr") final Client client) {

        System.out.println("Success!");
        return "redirect:/orders";
//                + order.getOrderId();
    }
    @RequestMapping("/save")
    public String saveSeveral(@ModelAttribute("orderAtr") final Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping("/new")
    public String newOrder(Model model) {
        OrderWrapper ordersWrapper = new OrderWrapper();
        ordersWrapper.addOrder(new Order());
        Client client = new Client();
        List<Item> itemList = itemService.getAll();

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("ordersWrapperAtr", ordersWrapper);
        model.addAttribute("clientAtr", client);

        return "newOrder";
    }
}

