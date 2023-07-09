package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.ServiceStatus;
import ru.bisha.easycrm.dto.*;
import ru.bisha.easycrm.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/rest/orders")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ui", havingValue = "rest")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public GetOrdersResponse getAllOrders(@RequestParam(required = false, defaultValue = "100") Integer size,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false) Integer statusId) {
        return orderService.getAll(size, page, statusId);
    }

    @GetMapping("/{id}")
    public SingleOrderDto getOne(@PathVariable Integer id) {
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}")
    public void updateOne(@RequestBody SingleOrderDto request) {
        orderService.updateOrder(request);
    }

    @PutMapping("/close")
    public void closeOrder(@RequestBody SingleOrderDto request) {
        orderService.closeOrder(request);
    }

    @PutMapping("/readyForCustomer")
    public void setReadyForCustomer(@RequestBody SingleOrderDto request) {
        orderService.setOrderReadyForCustomer(request);
    }

    @PostMapping("/new")
    public void createOrder(@RequestBody NewOrderDto request) {
        orderService.createOrder(request);
    }

    @GetMapping
    public List<OrderDto> getByClient(@RequestParam Integer clientId) {
        return orderService.getByClientId(clientId);
    }

    @GetMapping("/byUser")
    public ByUserAndServiceStatusResponse getOrdersByUser(@RequestParam Integer userId, @RequestParam ServiceStatus status,
                                                          @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        return orderService.getByUserIdAndStatus(userId, status, year, month);
    }
}
