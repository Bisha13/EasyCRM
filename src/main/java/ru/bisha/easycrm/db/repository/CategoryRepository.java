package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
