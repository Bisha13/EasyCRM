package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.repository.ItemRepository;
import ru.bisha.easycrm.dto.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/items")
@RequiredArgsConstructor
public class ItemsRestController {

    private final ItemRepository itemRepository;

    @GetMapping("/all")
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(i -> ItemDto.builder()
                .id(String.valueOf(i.getId()))
                .price(i.getPrice())
                .name(i.getName())
                .description(i.getDescription())
                .build()).collect(Collectors.toList());
    }
}
