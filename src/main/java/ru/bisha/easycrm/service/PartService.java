package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.PartEntity;

import java.util.List;
import java.util.Optional;

public interface PartService {

    void deletePart(int id);

    List<PartEntity> getAll();

    Optional<PartEntity> getById(int id);

}
