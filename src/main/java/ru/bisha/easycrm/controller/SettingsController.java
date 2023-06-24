package ru.bisha.easycrm.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settigns")
@ConditionalOnProperty(value = "ui", havingValue = "thymeleaf")
public class SettingsController {

    @RequestMapping
    public String getSettings() {
        return "settings";
    }
}
