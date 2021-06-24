package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.service.ServiceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class ServiceController {

        @Autowired
        ServiceService serviceService;

        @RequestMapping("/delete")
        public String deleteService(@RequestParam("itemId") final int id,
                                    HttpServletRequest request) {
            ServiceController.this.serviceService.deleteService(id);
            return "redirect:" + Optional.of(request.getHeader("referer"))
                    .orElse("/orders/259");
        }
}
