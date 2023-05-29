package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<ItemEntity> getAll();

    Optional<ItemEntity> getById(int id);

    void save(ItemEntity item);

    void delete(int id);

}
