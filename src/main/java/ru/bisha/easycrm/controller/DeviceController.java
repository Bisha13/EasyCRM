package ru.bisha.easycrm.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bisha.easycrm.db.entity.Client;
import ru.bisha.easycrm.db.entity.Device;
import ru.bisha.easycrm.service.ClientService;
import ru.bisha.easycrm.service.DeviceService;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/{id}")
    public String getDevice(@PathVariable("id") final int id, Model model) {
        var device = deviceService.getDevice(id);
        var owner = clientService.getClient(device.getOwnerId());
        model.addAttribute("deviceAtr", device);
        model.addAttribute("clientAtr", owner);
        return "device";
    }

    @RequestMapping("/save")
    public String saveDevice(@ModelAttribute("deviceAtr") final Device device) {
        deviceService.saveDevice(device);
        return "redirect:/devices/" + device.getDeviceId();
    }

}
