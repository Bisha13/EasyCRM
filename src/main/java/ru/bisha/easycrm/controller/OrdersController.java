package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.ServiceStatus;
import ru.bisha.easycrm.dto.*;
import ru.bisha.easycrm.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/rest/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/all")
    public GetOrdersResponse getAllOrders(@RequestParam(required = false, defaultValue = "100") Integer size,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false) Integer statusId) {
        return ordersService.getAll(size, page, statusId);
    }

    @GetMapping("/{id}")
    public SingleOrderDto getOne(@PathVariable Integer id) {
        return ordersService.getOrder(id);
    }

    @PutMapping("/{id}")
    public void updateOne(@RequestBody SingleOrderDto request) {
        ordersService.updateOrder(request);
    }

    @PutMapping("/close")
    public void closeOrder(@RequestBody SingleOrderDto request) {
        ordersService.closeOrder(request);
    }

    @PutMapping("/readyForCustomer")
    public void setReadyForCustomer(@RequestBody SingleOrderDto request) {
        ordersService.setOrderReadyForCustomer(request);
    }

    @PostMapping("/new")
    public void createOrder(@RequestBody NewOrderDto request) {
        ordersService.createOrder(request);
    }

    @GetMapping
    public List<OrderDto> getByClient(@RequestParam Integer clientId) {
        return ordersService.getByClientId(clientId);
    }

    @GetMapping("/byUser")
    public ByUserAndServiceStatusResponse getOrdersByUser(@RequestParam Integer userId, @RequestParam ServiceStatus status,
                                                          @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        return ordersService.getByUserIdAndStatus(userId, status, year, month);
    }
}
