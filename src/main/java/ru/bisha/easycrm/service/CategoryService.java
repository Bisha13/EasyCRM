package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> getAll();
}
