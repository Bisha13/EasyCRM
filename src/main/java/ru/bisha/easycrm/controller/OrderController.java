package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bisha.easycrm.db.entity.*;
import ru.bisha.easycrm.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class OrderController {


    private final OrderService orderService;

    private final UserService userService;

    private final ClientService clientService;

    private final DeviceService deviceService;

    private final ItemService itemService;


    @RequestMapping("orders")
    public String getAllOrders(Model model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderListAttr", orderList);
        return "allOrders";
    }

    @RequestMapping("orders/{id}")
    public String getOrder(@PathVariable int id, Model model) {
        Order order = orderService.getOrder(id);
        List<User> users = userService.getAllUsers();
        List<Item> items = itemService.getAll();
//        order.getListOfWorks().add(new Work());
        model.addAttribute("orderAtr", order);
        model.addAttribute("usersAtr", users);
        model.addAttribute("itemsAtr", items);
        return "order";
    }

    @RequestMapping("orders/saveWrapper")
    public String saveOrder(@ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper,
                            @ModelAttribute("clientAtr") final Client client) {
        Client someClient = clientService.saveClient(client);

        for (Order order : orderWrapper.getOrderList()) {
            order.setClient(someClient);
            order.getDevice().setOwnerId(someClient.getId());
            for (Work work : order.getListOfWorks()) {
                work.setOrder(order);
            }
            orderService.saveOrder(order);
        }
        return "redirect:/orders";
    }

    @RequestMapping("orders/save")
    public String saveSeveral(@ModelAttribute("orderAtr") final Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping
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

    @RequestMapping("orders/findClient")
    public String findClientAndLoadIt(@ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper,
                                      @ModelAttribute("clientAtr") final Client client, Model model) {
        List<Item> itemList = itemService.getAll();

        Client foundClient = clientService
                .findClientByNumber(client.getPhoneNumber());

        List<Device> deviceList = new ArrayList<>();
        if (foundClient != null) {
            deviceList = deviceService
                    .getDevicesByUserId(foundClient.getId());
        }

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("ordersWrapperAtr", orderWrapper);
        model.addAttribute("clientAtr",
                foundClient != null ?
                        foundClient : client);
        if (!deviceList.isEmpty()) {
            model.addAttribute("devisesAtr", deviceList);
        }
        return "newOrder";
    }

}

