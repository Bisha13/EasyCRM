package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
}
