package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.dto.GetOrdersResponse;
import ru.bisha.easycrm.dto.NewOrderDto;
import ru.bisha.easycrm.dto.OrderDto;
import ru.bisha.easycrm.dto.SingleOrderDto;
import ru.bisha.easycrm.restservice.RestOrderService;

import java.util.List;

@RestController
@RequestMapping("/rest/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final RestOrderService restOrderService;

    @GetMapping("/all")
    public GetOrdersResponse getAllOrders(@RequestParam(required = false, defaultValue = "100") Integer size,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false) Integer statusId) {
        return restOrderService.getAll(size, page, statusId);
    }

    @GetMapping("/{id}")
    public SingleOrderDto getOne(@PathVariable Integer id) {
        return restOrderService.getOrder(id);
    }

    @PutMapping("/{id}")
    public void updateOne(@RequestBody SingleOrderDto request) {
        restOrderService.updateOrder(request);
    }

    @PutMapping("/close")
    public void closeOrder(@RequestBody SingleOrderDto request) {
        restOrderService.closeOrder(request);
    }

    @PostMapping("/new")
    public void createOrder(@RequestBody NewOrderDto request) {
        restOrderService.createOrder(request);
    }

    @GetMapping
    public List<OrderDto> getByClient(@RequestParam Integer clientId) {
        return restOrderService.getByClientId(clientId);
    }
}
