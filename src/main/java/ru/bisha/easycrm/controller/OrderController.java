package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.*;
import ru.bisha.easycrm.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class OrderController {


    private final OrderService orderService;

    private final UserService userService;

    private final ClientService clientService;

    private final DeviceService deviceService;

    private final ItemService itemService;

    private static final int DEFAULT_PAGE_SIZE = 100;


    @RequestMapping("orders/")
    public String getAllOrders(Model model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderListAttr", orderList);
        return "allOrders";
    }

    @RequestMapping("orders/page/")
    public String getAllOrders(
                    Model model,
                    @RequestParam("page") Optional<Integer> page,
                    @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<Order> orderPage = orderService.getPageOfOrders(
                PageRequest.of(currentPage - 1, pageSize,
                        Sort.by("orderId").descending()));

        model.addAttribute("orderListAttr", orderPage);

        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "allOrders";
    }

    @RequestMapping("orders/{id}")
    public String getOrder(@PathVariable int id, Model model) {
        var order = orderService.getOrder(id);

        try {
            order.getDevice();
        } catch (Exception e) {
            order.setDevice(new Device());
        }

        List<User> users = userService.getAllUsers();
        List<Item> items = itemService.getAll();

        model.addAttribute("orderAtr", order);
        model.addAttribute("usersAtr", users);
        model.addAttribute("itemsAtr", items);
        return "order";
    }

    @RequestMapping("orders/saveWrapper")
    public String saveOrder(@ModelAttribute("ordersWrapperAtr")
                                final OrderWrapper orderWrapper,
                            @ModelAttribute("clientAtr") final Client client) {
        var someClient = clientService.saveClient(client);

        for (Order order : orderWrapper.getOrderList()) {
            order.setClient(someClient);
            order.getDevice().setOwnerId(someClient.getId());
            for (Service service : order.getListOfServices()) {
                service.setOrder(order);
            }
            orderService.saveOrder(order);
        }
        return "redirect:/orders/page/?size=" + DEFAULT_PAGE_SIZE + "&page=1";
    }

    @RequestMapping("orders/save")
    public String saveSeveral(@ModelAttribute("orderAtr") final Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping
    public String newOrder(Model model) {
        var ordersWrapper = new OrderWrapper();
        ordersWrapper.addOrder(new Order());
        var client = new Client();
        List<Item> itemList = itemService.getAll();

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("ordersWrapperAtr", ordersWrapper);
        model.addAttribute("clientAtr", client);

        return "newOrder";
    }

    @RequestMapping("orders/findClient")
    public String findClientAndLoadIt(
            @ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper,
            @ModelAttribute("clientAtr") final Client client, Model model) {
        List<Item> itemList = itemService.getAll();

        var foundClient = clientService
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

