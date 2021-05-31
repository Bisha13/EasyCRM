package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Client;

public interface ClientService {

    Client getClient(int id);

    Client saveClient(Client client);

    Client findClientByNumber(String phoneNumber);
}
