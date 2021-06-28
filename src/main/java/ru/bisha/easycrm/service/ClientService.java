package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Client;

import java.util.List;

public interface ClientService {

    Client getClient(int id);

    Client saveClient(Client client);

    Client findClientByNumber(String phoneNumber);

    List<Client> findClientByPhone(String phoneNumber);
}
