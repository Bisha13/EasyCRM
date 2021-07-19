package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.service.PartService;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartService partService;

    @RequestMapping("/delete")
    public String deletePart(@RequestParam("itemId") final String id,
                                HttpServletRequest request) {
        try {
            int parsedId = Integer.parseInt(id);
            partService.deletePart(parsedId);
        } catch (NumberFormatException ignored) {
        }
        return "redirect:" + Optional.of(request.getHeader("referer"))
                .orElse("/orders/259");
    }

}
