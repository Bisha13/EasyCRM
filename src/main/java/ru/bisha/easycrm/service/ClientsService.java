package ru.bisha.easycrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.ClientEnitiy;
import ru.bisha.easycrm.db.repository.ClientRepository;

import java.util.List;

@Service
public class ClientsService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientEnitiy getClient(int id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public ClientEnitiy saveClient(ClientEnitiy client) {
        return clientRepository.save(client);
    }

    public ClientEnitiy findClientByNumber(String phoneNumber) {
        String shorter = phoneNumber.replaceAll("[()+-]", "");
        if (shorter.length() >= 11 && shorter.startsWith("7")) {
            shorter = shorter.replaceFirst("7", "");
        }
        String phoneLike = "%" + shorter;
        return clientRepository.findClientByPhoneNumberLike(phoneLike);
    }

    public List<ClientEnitiy> findClientByPhone(String phoneNumber) {
        var parsedPhoneNumber = phoneNumber.trim().replaceAll("[^0-9]", "");

        if (parsedPhoneNumber.length() >= 10 //9153332211 - 10 characters
                && parsedPhoneNumber.startsWith("7")) {
            parsedPhoneNumber = parsedPhoneNumber.replaceFirst("7", "");
        }

        if (parsedPhoneNumber.length() >= 10 //9153332211 - 10 characters
                && parsedPhoneNumber.startsWith("8")) {
            parsedPhoneNumber = parsedPhoneNumber.replaceFirst("8", "");
        }

        return clientRepository
                .findClientsByPhoneNumberContains(parsedPhoneNumber);
    }

    public Page<ClientEnitiy> getPageOfClients(PageRequest request) {
        return clientRepository.findAll(request);
    }

    public Page<ClientEnitiy> getPageOfClientsBySearch(String search,
                                                       PageRequest request) {
        if ((search.startsWith("+")
                || search.startsWith("7")
                || search.startsWith("8")) && search.length() >= 10 ) {
            search = search.substring(2).replaceAll("[^0-9]", "");
        }
        return clientRepository.getClientsBySearch("%" + search + "%", request);
    }
}
