package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> getAll();

    Optional<Item> getById(int id);

    void save(Item item);

    void delete(int id);

}
