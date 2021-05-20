package ru.bisha.easycrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Client;
import ru.bisha.easycrm.db.repository.ClientRepository;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client getClient(int id) {
        return clientRepository.findById(id).orElseThrow();
    }

}
