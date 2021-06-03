package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.service.WorkService;

@Controller
@RequestMapping("/works")
public class WorkController {

    @Autowired
    WorkService workService;

    @RequestMapping("/delete")
    String deleteWork(@RequestParam("itemId") final int id) {
        workService.deleteWork(id);
        return "redirect:/orders/259";
    }
}
