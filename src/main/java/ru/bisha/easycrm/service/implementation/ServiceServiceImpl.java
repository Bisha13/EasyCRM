package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.repository.ServiceRepository;
import ru.bisha.easycrm.service.ServiceService;

import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository repository;

    @Override
    public Optional<ru.bisha.easycrm.db.entity.Service> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void deleteService(int id) {
        repository.deleteById(id);
    }
}
