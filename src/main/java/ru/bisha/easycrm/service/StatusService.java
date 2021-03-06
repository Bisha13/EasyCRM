package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Status;

import java.util.List;

public interface StatusService {

    List<Status> getAll();
    Status findById(long id);
    void save(Status status);
}
