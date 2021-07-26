package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Service;

import java.util.Optional;

public interface ServiceService {
    void deleteService(int id);
    Optional<Service> getById(int id);
}
