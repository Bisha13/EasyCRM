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

import java.sql.Date;
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

    private final StockService stockService;

    private static final int DEFAULT_PAGE_SIZE = 100;

    private static final String DEFAULT_ALL_ORDERS_REDIRECT

            = "redirect:/orders/page/?size=" + DEFAULT_PAGE_SIZE + "&page=1";


    @RequestMapping("orders/page/")
    public String getAllOrders(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("statusId") Optional<Integer> status) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<Order> orderPage;

        if (status.isEmpty()) {
            orderPage = orderService.getPageOfOrders(
                    PageRequest.of(currentPage - 1, pageSize,
                            Sort.by("orderId")));
        } else {

            if (status.get() == -1) {
                orderPage = orderService.getAllNotHidden(PageRequest.of
                        (currentPage - 1, pageSize,
                                Sort.by("orderId")));
            } else {
                orderPage = orderService.getByStatusId(status.get(),
                        PageRequest.of(currentPage - 1, pageSize,
                                Sort.by("orderId")));
            }
        }

        model.addAttribute("statusesAtr", statusService.getAll());
        model.addAttribute("orderListAttr", orderPage);
        model.addAttribute("statusAtr", new Status());

        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "allOrders";
    }

    @RequestMapping("searchOrder")
    public String searchOrder(@RequestParam("id") String id) {
        if (id.isEmpty()) {
            return DEFAULT_ALL_ORDERS_REDIRECT;
        }
        return "redirect:/orders/" + Integer.parseInt(id);
    }

    @RequestMapping("orders/{id}")
    public String getOrder(@PathVariable int id, Model model) {
        var order = orderService.getOrder(id);
        if (order.getOrderId() == 0) {
            return "redirect:/orders/page/?size="
                    + DEFAULT_PAGE_SIZE + "&page=1";
        }

        List<Status> statuses = statusService.getAll();
        List<User> users = userService.getAllUsers();
        List<Item> items = itemService.getAll();
        List<Stock> stockList = stockService.getAllStockParts();

        try {
            order.getDevice();
        } catch (Exception e) {
            order.setDevice(new Device());
        }

        if (order.getListOfParts().isEmpty()) {
            var listOfParts = new ArrayList<Part>();
            listOfParts.add(new Part(stockList.get(0)));
            order.setListOfParts(listOfParts);
        }

        if (order.getListOfServices().isEmpty()) {
            var listOfServices = new ArrayList<ServiceEntity>();
            listOfServices.add(new ServiceEntity());
            order.setListOfServices(listOfServices);
        }

        model.addAttribute("stockAtr", stockList);
        model.addAttribute("statusesAtr", statuses);
        model.addAttribute("orderAtr", order);
        model.addAttribute("usersAtr", users);
        model.addAttribute("itemsAtr", items);
        return "order";
    }

    @RequestMapping("orders/saveWrapper")
    public String saveOrder(@ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper) {
        var someClient =
                clientService.saveClient(orderWrapper.getClient());

        for (Order order : orderWrapper.getOrderList()) {
            order.setClient(someClient);
            order.getDevice().setOwnerId(someClient.getId());
            for (ServiceEntity service : order.getListOfServices()) {
                service.setOrder(order);
            }
            for (Part part : order.getListOfParts()) {
                part.setOrder(order);
            }
            orderService.saveOrder(order);
        }
        return "redirect:/orders/page/?size=" + DEFAULT_PAGE_SIZE +
                "&page=1&statusId=-1";
    }

    @RequestMapping("orders/save")
    public String saveOrder(@ModelAttribute("orderAtr") final Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping("orders/close")
    public String closeOrder(@ModelAttribute("orderAtr") final Order order) {
        order.setTimeClose(new Date(new java.util.Date().getTime()));
        order.setExecuteStatus(statusService.findById(14));
        orderService.saveOrder(order);
        return "redirect:/orders/" + order.getOrderId();
    }

    @RequestMapping
    public String newOrder(Model model) {
        var ordersWrapper = new OrderWrapper();
        List<Item> itemList = itemService.getAll();
        List<Stock> stockList = stockService.getAllStockParts();
        ordersWrapper.addOrder(
                new Order(itemList.get(0),
                        stockList.get(0),
                        statusService.findById(1)));

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("stockAtr", stockList);
        model.addAttribute("ordersWrapperAtr", ordersWrapper);

        return "newOrder";
    }

    @RequestMapping("orders/findClient")
    public String findClientAndLoadIt(@ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper, Model model) {

        var client = orderWrapper.getClient();
        var foundClient = clientService
                .findClientByNumber(client.getPhoneNumber());

        List<Device> deviceList = new ArrayList<>();
        if (foundClient != null) {
            orderWrapper.setClient(foundClient);
            deviceList = deviceService
                    .getDevicesByUserId(foundClient.getId());
        }
        List<Stock> stockList = stockService.getAllStockParts();

        model.addAttribute("stockAtr", stockList);
        model.addAttribute("itemsAtr", itemService.getAll());
        model.addAttribute("ordersWrapperAtr", orderWrapper);
        if (!deviceList.isEmpty()) {
            model.addAttribute("devisesAtr", deviceList);
        }
        return "newOrder";
    }

    @RequestMapping("orders/findByPhoneNumber")
    public String findByPhoneNumber(
            @ModelAttribute("ordersWrapperAtr") final OrderWrapper orderWrapper, Model model) {

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
        List<Stock> stockList = stockService.getAllStockParts();

        model.addAttribute("stockAtr", stockList);
        model.addAttribute("ordersWrapperAtr", orderWrapper);
        model.addAttribute("itemsAtr", itemService.getAll());
        return "newOrder";
    }

    @RequestMapping("/orders/new")
    public String createNewFromClient(
            @RequestParam("clientId") final int id, Model model) {
        var ordersWrapper = new OrderWrapper();
        var client = clientService.getClient(id);
        ordersWrapper.setClient(client);

        List<Item> itemList = itemService.getAll();
        List<Stock> stockList = stockService.getAllStockParts();
        var deviceList = deviceService.getDevicesByUserId(id);
        var newOrder = new Order(itemList.get(0), stockList.get(0),
                statusService.findById(1));
        newOrder.setClient(client);
        ordersWrapper.addOrder(
                new Order(itemList.get(0),
                        stockList.get(0),
                        statusService.findById(1)));

        model.addAttribute("itemsAtr", itemList);
        model.addAttribute("stockAtr", stockList);
        model.addAttribute("ordersWrapperAtr", ordersWrapper);
        if (!deviceList.isEmpty()) {
            model.addAttribute("devisesAtr", deviceList);
        }

        return "newOrder";
    }
}

