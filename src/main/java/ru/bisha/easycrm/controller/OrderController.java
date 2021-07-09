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

    private final StatusService statusService;

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

        if (order.getListOfParts().isEmpty()) {
            var listOfParts = new ArrayList<Part>();
            listOfParts.add(new Part());
            order.setListOfParts(listOfParts);
        }

        if (order.getListOfServices().isEmpty()) {
            var listOfServices = new ArrayList<Service>();
            listOfServices.add(new Service());
            order.setListOfServices(listOfServices);
        }

        List<Status> statuses = statusService.getAll();
        List<User> users = userService.getAllUsers();
        List<Item> items = itemService.getAll();

        model.addAttribute("statusesAtr", statuses);
        model.addAttribute("orderAtr", order);
        model.addAttribute("usersAtr", users);
        model.addAttribute("itemsAtr", items);
        return "order";
    }

    @RequestMapping("orders/saveWrapper")
    public String saveOrder(@ModelAttribute("ordersWrapperAtr")
                                final OrderWrapper orderWrapper) {
        var someClient =
                clientService.saveClient(orderWrapper.getClient());

        for (Order order : orderWrapper.getOrderList()) {
            order.setClient(someClient);
            order.getDevice().setOwnerId(someClient.getId());
            for (Service service : order.getListOfServices()) {
                service.setOrder(order);
            }
            for (Part part : order.getListOfParts()) {
                part.setOrder(order);
            }
            orderService.saveOrder(order);
        }
        return "redirect:/orders/page/?size=" + DEFAULT_PAGE_SIZE + "&page=1";
    }

    @RequestMapping("orders/save")
    public String saveOrder(@ModelAttribute("orderAtr") final Order order) {
        for (Part part : order.getListOfParts()) {
            part.setOrder(order);
        }
        for (Service service : order.getListOfServices()) {
            service.setOrder(order);
        }
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping
    public String newOrder(Model model) {
        var ordersWrapper = new OrderWrapper();
        List<Item> itemList = itemService.getAll();
        ordersWrapper.addOrder(
                new Order(itemList.get(0), statusService.findById(1)));

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("ordersWrapperAtr", ordersWrapper);

        return "newOrder";
    }

    @RequestMapping("orders/findClient")
    public String findClientAndLoadIt(@ModelAttribute("ordersWrapperAtr")
                                 final OrderWrapper orderWrapper, Model model) {

        var client = orderWrapper.getClient();
        var foundClient = clientService
                .findClientByNumber(client.getPhoneNumber());

        List<Device> deviceList = new ArrayList<>();
        if (foundClient != null) {
            orderWrapper.setClient(foundClient);
            deviceList = deviceService
                    .getDevicesByUserId(foundClient.getId());
        }


        model.addAttribute("itemsAtr", itemService.getAll());
        model.addAttribute("ordersWrapperAtr", orderWrapper);
        if (!deviceList.isEmpty()) {
            model.addAttribute("devisesAtr", deviceList);
        }
        return "newOrder";
    }

    @RequestMapping("orders/findByPhoneNumber")
    public String findByPhoneNumber(
            @ModelAttribute("ordersWrapperAtr")
            final OrderWrapper orderWrapper, Model model) {

        List<Client> clients
                = clientService.findClientByPhone(
                        orderWrapper.getClient().getPhoneNumber());

        if (clients.size() == 1) {
            var foundClient = clients.get(0);
            orderWrapper.setClient(foundClient);
            var deviceList = deviceService
                    .getDevicesByUserId(foundClient.getId());
            if (!deviceList.isEmpty()) {
                model.addAttribute("devisesAtr", deviceList);
            }
        }
        if (clients.size() > 1) {
            model.addAttribute("clientListAtr", clients);
        }
        model.addAttribute("ordersWrapperAtr", orderWrapper);
        model.addAttribute("itemsAtr", itemService.getAll());
        return "newOrder";
    }
}

