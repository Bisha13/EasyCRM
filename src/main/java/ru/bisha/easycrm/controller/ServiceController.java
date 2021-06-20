package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.service.ServiceService;

@Controller
@RequestMapping("/services")
public class ServiceController {

        @Autowired
        ServiceService serviceService;

        @RequestMapping("/delete")
        public String deleteService(@RequestParam("itemId") final int id) {
            ServiceController.this.serviceService.deleteService(id);
            return "redirect:/orders/259";
            //todo return proper order
        }
}
