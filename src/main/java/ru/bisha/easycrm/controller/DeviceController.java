package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.DeviceEntity;
import ru.bisha.easycrm.service.ClientService;
import ru.bisha.easycrm.service.DeviceService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/devices")
@ConditionalOnProperty(value = "ui", havingValue = "thymeleaf")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ClientService clientService;

    private static final int DEFAULT_PAGE_SIZE = 100;

    @RequestMapping("/{id}")
    public String getDevice(@PathVariable("id") final int id, Model model) {
        var device = deviceService.getDevice(id);
        var owner = clientService.getClient(device.getOwnerId());
        model.addAttribute("deviceAtr", device);
        model.addAttribute("clientAtr", owner);
        return "device";
    }

    @RequestMapping("/save")
    public String saveDevice(@ModelAttribute("deviceAtr") final DeviceEntity device) {
        deviceService.saveDevice(device);
        return "redirect:/devices/" + device.getDeviceId();
    }

    @RequestMapping("/page/")
    public String getAllDevices(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<DeviceEntity> devicePage = deviceService.getPageOfDevices(
                PageRequest.of(currentPage - 1, pageSize,
                        Sort.by("deviceId").descending()));

        model.addAttribute("deviceListAtr", devicePage);

        int totalPages = devicePage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "allDevices";
    }

    @RequestMapping("/searchDevice/")
    public String getAllDevices(Model model,
                                @RequestParam("search") String search,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<DeviceEntity> devicePage = deviceService.getDevicesBySearch(
                search, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("deviceListAtr", devicePage);

        int totalPages = devicePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "allDevices";
    }

    @RequestMapping("/new")
    public String createNew(@RequestParam("ownerId") final int id, Model model) {
        var device = new DeviceEntity();
        var owner = clientService.getClient(id);
        device.setOwnerId(owner.getId());
        model.addAttribute("deviceAtr", device);
        model.addAttribute("clientAtr", owner);
        return "device";
    }
}
