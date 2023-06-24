package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.dto.CategoryDto;
import ru.bisha.easycrm.service.CategoryService;
import ru.bisha.easycrm.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/categories")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ui", havingValue = "rest")
public class CategoryRestController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDto> getAllItems() {
        return  categoryService.getAll().stream()
                .map(c -> CategoryDto.builder()
                        .id(String.valueOf(c.getId()))
                        .name(c.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
