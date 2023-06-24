package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bisha.easycrm.db.entity.StatusWrapper;
import ru.bisha.easycrm.service.StatusService;

@Controller
@RequestMapping("/statuses")
@ConditionalOnProperty(value = "ui", havingValue = "thymeleaf")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @RequestMapping
    public String getAll(Model model) {
        StatusWrapper statusWrapper = new StatusWrapper();
        statusWrapper.setStatusList(statusService.getAll());
        model.addAttribute("statusWrapperAtr", statusWrapper);
        return "allStatuses";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute("statusWrapperAtr") StatusWrapper statusWrapper) {
        statusWrapper.getStatusList().forEach(s -> statusService.save(s));
        return "redirect:/statuses";
    }

}
