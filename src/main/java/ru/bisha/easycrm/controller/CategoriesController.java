package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.dto.CategoryDto;
import ru.bisha.easycrm.service.CategoriesService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @GetMapping("/all")
    public List<CategoryDto> getAllItems() {
        return  categoriesService.getAll().stream()
                .map(c -> CategoryDto.builder()
                        .id(String.valueOf(c.getId()))
                        .name(c.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
