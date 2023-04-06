package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.dto.GetOrdersResponse;
import ru.bisha.easycrm.restservice.RestOrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final RestOrderService restOrderService;

    @GetMapping("/all")
    public GetOrdersResponse getAllOrders(@RequestParam(required = false, defaultValue = "100") Integer size,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false) Integer statusId) {
        return restOrderService.getAll(size, page, statusId);
    }
}
