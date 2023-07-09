package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.CategoryEntity;
import ru.bisha.easycrm.db.entity.ItemEntity;
import ru.bisha.easycrm.db.entity.ServiceEntity;
import ru.bisha.easycrm.db.repository.CategoryRepository;
import ru.bisha.easycrm.db.repository.ItemRepository;
import ru.bisha.easycrm.db.repository.ServiceRepository;
import ru.bisha.easycrm.dto.ItemDto;
import ru.bisha.easycrm.service.ItemsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemsService itemsService;
    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @GetMapping("/all")
    public List<ItemDto> getAllItems() {
        return itemsService.getAll().stream().map(i -> ItemDto.builder()
                .id(String.valueOf(i.getId()))
                .name(getName(i))
                .description(i.getDescription())
                .categoryId(String.valueOf(i.getCategory().getId()))
                .priority(i.getPriority())
                .price(i.getPrice())
                .build()).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable int id) {
        return itemsService.getById(id).map(i -> ItemDto.builder()
                .id(String.valueOf(i.getId()))
                .name(getName(i))
                .description(i.getDescription())
                .categoryId(String.valueOf(i.getCategory().getId()))
                .priority(i.getPriority())
                .price(i.getPrice())
                .build()).orElseThrow();
    }

    @PutMapping
    public void saveItem(@RequestBody ItemDto itemDto) {
        CategoryEntity category = categoryRepository.findById(Integer.valueOf(itemDto.getCategoryId())).orElseThrow();
        ItemEntity item = ItemEntity.builder()
                .id(Optional.ofNullable(itemDto.getId()).map(Integer::parseInt).orElse(null))
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .price(itemDto.getPrice())
                .priority(itemDto.getPriority())
                .category(category)
                .build();
        if (item.getId() != null) {
            ItemEntity emptyItem = itemRepository.findById(0).orElseThrow();
            List<ServiceEntity> services = serviceRepository.getAllByItemId(item.getId());
            services.forEach(e -> {
                e.setIsCustom(true);
                e.setDescription(item.getName());
                e.setItem(emptyItem);
                e.setPrice(item.getPrice());
            });
        }
        itemsService.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemsService.delete(id);
    }

    private static String getName(ItemEntity i) {
        return Optional.ofNullable(i.getName())
                .map(String::trim)
                .map(s -> s.replace("\s\s", " "))
                .orElse("");
    }
}
