package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Part;

import java.util.List;
import java.util.Optional;

public interface PartService {

    void deletePart(int id);

    List<Part> getAll();

    Optional<Part> getById(int id);

}
