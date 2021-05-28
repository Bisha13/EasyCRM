package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Item;
import ru.bisha.easycrm.db.repository.ItemRepository;
import ru.bisha.easycrm.service.ItemService;

import java.util.List;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll(Sort.by("category_id")
                                        .and(Sort.by("priority")));
    }
}
