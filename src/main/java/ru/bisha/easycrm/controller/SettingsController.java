package ru.bisha.easycrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settigns")
public class SettingsController {

    @RequestMapping
    public String getSettings() {
        return "settings";
    }
}
