package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.repository.ServiceRepository;
import ru.bisha.easycrm.service.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository repository;

    @Override
    public void deleteService(int id) {
        repository.deleteById(id);
    }
}
