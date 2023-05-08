package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.ServiceEntity;

import java.util.Optional;

public interface ServiceService {
    void deleteService(int id);
    Optional<ServiceEntity> getById(int id);
}
