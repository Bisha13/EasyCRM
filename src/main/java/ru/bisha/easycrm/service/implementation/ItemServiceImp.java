package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Item;
import ru.bisha.easycrm.db.repository.ItemRepository;
import ru.bisha.easycrm.db.repository.ServiceRepository;
import ru.bisha.easycrm.service.ItemService;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll(Sort.by("category_id")
                                        .and(Sort.by("priority")));
    }

    @Override
    public Optional<Item> getById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void delete(int id) {

        var item = itemRepository.findById(id);
        if (item.isEmpty()) {
            return;
        }

        var services = serviceRepository.getAllByItemId(id);
        services.forEach(e -> {
            e.setIsCustom(true);
            e.setDescription(item.get().getName());
            e.setItem(itemRepository.findById(0).orElseThrow());
            e.setPrice(item.get().getPrice());
        });

        itemRepository.deleteById(id);
    }
}
