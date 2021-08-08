package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Client;
import ru.bisha.easycrm.db.repository.ClientRepository;
import ru.bisha.easycrm.service.ClientService;

import java.util.List;

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

    @Override
    public Client findClientByNumber(String phoneNumber) {
        String shorter = phoneNumber.replaceAll("[()+-]", "");
        if (shorter.length() >= 11 && shorter.startsWith("7")) {
            shorter = shorter.replaceFirst("7", "");
        }
        String phoneLike = "%" + shorter;
        return clientRepository.findClientByPhoneNumberLike(phoneLike);
    }

    @Override
    public List<Client> findClientByPhone(String phoneNumber) {
        var parsedPhoneNumber = phoneNumber.trim().replaceAll("[^0-9]", "");

        if (parsedPhoneNumber.length() >= 10 //9153332211 - 10 characters
                && parsedPhoneNumber.startsWith("7")) {
            parsedPhoneNumber = parsedPhoneNumber.replaceFirst("7", "");
        }

        return clientRepository
                .findClientsByPhoneNumberContains(parsedPhoneNumber);
    }

    @Override
    public Page<Client> getPageOfClients(PageRequest request) {
        return clientRepository.findAll(request);
    }

    @Override
    public Page<Client> getPageOfClientsBySearch(String search,
                                                 PageRequest request) {
        if ((search.startsWith("+")
                || search.startsWith("7")
                || search.startsWith("8")) && search.length() >= 10 ) {
            search = search.substring(2).replaceAll("[^0-9]", "");
        }
        return clientRepository.getClientsBySearch("%" + search + "%", request);
    }
}
