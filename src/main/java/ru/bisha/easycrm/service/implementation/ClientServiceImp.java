package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Client;
import ru.bisha.easycrm.db.repository.ClientRepository;
import ru.bisha.easycrm.service.ClientService;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client getClient(int id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

}
