package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.ClientEnitiy;
import ru.bisha.easycrm.dto.ClientDto;
import ru.bisha.easycrm.dto.GetClientsResponse;
import ru.bisha.easycrm.service.ClientsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/clients")
@RequiredArgsConstructor
public class ClientsController {

    private final ClientsService clientsService;

    private static final String DEFAULT_PAGE_SIZE = "100";

    @GetMapping("/by_phone")
    public List<ClientDto> getClientByPhone(@RequestParam String phone) {
        List<ClientEnitiy> clients
                = clientsService.findClientByPhone(phone);
        return clients.stream().map(c -> ClientDto.builder()
                .id(String.valueOf(c.getId()))
                .name(c.getName())
                .phone(c.getPhoneNumber())
                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDto getClientByPhone(@PathVariable Integer id) {
        ClientEnitiy client = clientsService.getClient(id);
        return ClientDto.builder()
                .id(String.valueOf(client.getId()))
                .name(client.getName())
                .phone(client.getPhoneNumber())
                .phone2(client.getPhoneNumber2())
                .address(client.getAddress())
                .discount(client.getDiscount())
                .createdAt(client.getTimestamp().toLocalDateTime())
                .build();
    }

    @PutMapping("/")
    public void updateClient(@RequestBody ClientDto clientDto) {
        ClientEnitiy client = clientsService.getClient(Integer.parseInt(clientDto.getId()));
        client.setName(clientDto.getName());
        client.setPhoneNumber(clientDto.getPhone());
        client.setPhoneNumber2(clientDto.getPhone2());
        client.setAddress(clientDto.getAddress());
        client.setDiscount(clientDto.getDiscount());
        clientsService.saveClient(client);
    }

    @GetMapping
    public GetClientsResponse getAllClients(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

        Page<ClientEnitiy> clientPage = clientsService.getPageOfClients(
                PageRequest.of(page - 1, size,
                        Sort.by("id").descending()));

        int totalPages = clientPage.getTotalPages();

        List<ClientDto> clients = clientPage.stream().map(client -> ClientDto.builder()
                    .id(String.valueOf(client.getId()))
                    .name(client.getName())
                    .phone(client.getPhoneNumber())
                    .createdAt(client.getTimestamp().toLocalDateTime())
                    .build())
                .collect(Collectors.toList());

        return GetClientsResponse.builder()
                .clients(clients)
                .pageCount(totalPages)
                .build();
    }

    @GetMapping("/find")
    public GetClientsResponse findClients(@RequestParam("search") String search,
                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        Page<ClientEnitiy> clientsPage = clientsService.getPageOfClientsBySearch(
                search, PageRequest.of(page - 1, size));
        int totalPages = clientsPage.getTotalPages();
        List<ClientDto> clients = clientsPage.stream().map(client -> ClientDto.builder()
                        .id(String.valueOf(client.getId()))
                        .name(client.getName())
                        .phone(client.getPhoneNumber())
                        .createdAt(client.getTimestamp().toLocalDateTime())
                        .build())
                .collect(Collectors.toList());

        return GetClientsResponse.builder()
                .clients(clients)
                .pageCount(totalPages)
                .build();
    }
}
