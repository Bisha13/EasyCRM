package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.entity.Client;
import ru.bisha.easycrm.dto.ClientDto;
import ru.bisha.easycrm.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/clients")
@RequiredArgsConstructor
public class ClientsRestController {


    private final ClientService clientService;

    @GetMapping("/by_phone")
    public List<ClientDto> getClientByPhone(@RequestParam String phone) {
        List<Client> clients
                = clientService.findClientByPhone(phone);
        return clients.stream().map(c -> ClientDto.builder()
                .id(String.valueOf(c.getId()))
                .name(c.getName())
                .phone(c.getPhoneNumber())
                .build())
                .collect(Collectors.toList());
    }

}
