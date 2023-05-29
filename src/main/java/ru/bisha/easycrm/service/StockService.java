package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.StockEntity;

import java.util.List;
import java.util.Optional;

public interface StockService {

    void deleteStockPart(int id);
    List<StockEntity> getAllStockParts();
    void updateStockPart(StockEntity part);
    void save(StockEntity part);
    Optional<StockEntity> getById(int id);

}
