package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
