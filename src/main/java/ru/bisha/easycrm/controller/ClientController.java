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
import ru.bisha.easycrm.db.entity.ClientEnitiy;
import ru.bisha.easycrm.db.entity.DeviceEntity;
import ru.bisha.easycrm.db.entity.OrderEntity;
import ru.bisha.easycrm.service.ClientService;
import ru.bisha.easycrm.service.DeviceService;
import ru.bisha.easycrm.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/clients")
@ConditionalOnProperty(value = "ui", havingValue = "thymeleaf")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeviceService deviceService;

    private static final int DEFAULT_PAGE_SIZE = 100;

    @RequestMapping("/{id}")
    public String getClient(@PathVariable("id") final int id, Model model) {
        var client = clientService.getClient(id);
        List<OrderEntity> orders = orderService.getOrdersByClientId(client.getId());
        List<DeviceEntity> devices = deviceService.getDevicesByUserId(client.getId());
        model.addAttribute("clientAtr", client);
        model.addAttribute("orderListAttr", orders);
        model.addAttribute("deviceListAtr", devices);

        return "client";
    }

    @RequestMapping("/save")
    public String saveClient(@ModelAttribute("clientAtr") final ClientEnitiy client) {
        clientService.saveClient(client);
        return "redirect:/clients/" + client.getId();
    }

    @RequestMapping("/page/" )
    public String getAllClients(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ClientEnitiy> clientPage = clientService.getPageOfClients(
                PageRequest.of(currentPage - 1, pageSize,
                        Sort.by("id").descending()));

        model.addAttribute("clientListAtr", clientPage);

        int totalPages = clientPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "allClients";
    }

    @RequestMapping("/searchClient/")
    public String getAllClients(Model model,
                                @RequestParam("search") String search,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ClientEnitiy> clientsPage = clientService.getPageOfClientsBySearch(
                search, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("clientListAtr", clientsPage);

        int totalPages = clientsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "allClients";
    }

}
