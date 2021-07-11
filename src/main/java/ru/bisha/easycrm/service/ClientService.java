package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.Client;

import java.util.List;

public interface ClientService {

    Client getClient(int id);

    Client saveClient(Client client);

    Client findClientByNumber(String phoneNumber);

    List<Client> findClientByPhone(String phoneNumber);

    Page<Client> getPageOfClients(PageRequest request);

    Page<Client> getPageOfClientsBySearch(String search, PageRequest request);
}
