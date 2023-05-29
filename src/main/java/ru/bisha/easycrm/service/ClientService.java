package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.ClientEnitiy;

import java.util.List;

public interface ClientService {

    ClientEnitiy getClient(int id);

    ClientEnitiy saveClient(ClientEnitiy client);

    ClientEnitiy findClientByNumber(String phoneNumber);

    List<ClientEnitiy> findClientByPhone(String phoneNumber);

    Page<ClientEnitiy> getPageOfClients(PageRequest request);

    Page<ClientEnitiy> getPageOfClientsBySearch(String search, PageRequest request);
}
