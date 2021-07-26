package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.service.OrderService;
import ru.bisha.easycrm.service.ServiceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/delete")
    public String deleteService(@RequestParam("itemId") final int id,
                                HttpServletRequest request) {
        var service = serviceService.getById(id);
        if (service.isEmpty()) {
            return "redirect:" + Optional.of(request.getHeader("referer"))
                    .orElse("/orders/259");
        }
        var order = service.get().getOrder();
        if (id != 0) {
            serviceService.deleteService(id);
        }
        orderService.saveOrder(order);
        return "redirect:" + Optional.of(request.getHeader("referer"))
                .orElse("/orders/259");
    }
}
