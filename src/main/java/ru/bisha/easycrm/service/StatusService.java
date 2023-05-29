package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.StatusEntity;

import java.util.List;

public interface StatusService {

    List<StatusEntity> getAll();
    StatusEntity findById(long id);
    void save(StatusEntity status);
}
