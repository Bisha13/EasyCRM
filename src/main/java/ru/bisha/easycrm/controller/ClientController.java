package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bisha.easycrm.db.entity.Client;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.service.ClientService;
import ru.bisha.easycrm.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/{id}")
    public String getClient(@PathVariable("id") final int id, Model model) {
        var client = clientService.getClient(id);
        List<Order> orders = orderService.getOrdersByClientId(client.getId());
        model.addAttribute("clientAtr", client);
        model.addAttribute("orderListAttr", orders);
        return "client";
    }

    @RequestMapping("/save")
    public String saveClient(@ModelAttribute("clientAtr") final Client client) {
        clientService.saveClient(client);
        return "redirect:/clients/" + client.getId();
    }

}
