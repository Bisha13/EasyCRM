package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.CategoryEntity;
import ru.bisha.easycrm.db.repository.CategoryRepository;
import ru.bisha.easycrm.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<CategoryEntity> getAll() {
        return repository.findAll();
    }
}
