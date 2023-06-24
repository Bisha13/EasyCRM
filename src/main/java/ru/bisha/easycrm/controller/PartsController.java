package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.service.OrderService;
import ru.bisha.easycrm.service.PartService;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/parts")
@ConditionalOnProperty(value = "ui", havingValue = "thymeleaf")
public class PartsController {

    @Autowired
    private PartService partService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/delete")
    public String deletePart(@RequestParam("itemId") final String id,
                                HttpServletRequest request) {
        try {
            int parsedId = Integer.parseInt(id);
            var part = partService.getById(parsedId);
            if (part.isEmpty()) {
                return "redirect:" + Optional.of(request.getHeader("referer"))
                        .orElse("/orders/259");
            }
            var order = part.get().getOrder();
            partService.deletePart(parsedId);
            orderService.saveOrder(order);
        } catch (NumberFormatException ignored) {
        }
        return "redirect:" + Optional.of(request.getHeader("referer"))
                .orElse("/orders/259");
    }

}
