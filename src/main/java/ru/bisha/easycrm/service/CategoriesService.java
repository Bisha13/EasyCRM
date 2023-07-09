package ru.bisha.easycrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.CategoryEntity;
import ru.bisha.easycrm.db.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryEntity> getAll() {
        return repository.findAll();
    }
}
