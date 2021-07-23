package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.StatusWrapper;
import ru.bisha.easycrm.db.entity.Stock;
import ru.bisha.easycrm.service.StatusService;
import ru.bisha.easycrm.service.StockService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/statuses")
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
