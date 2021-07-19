package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    void deleteStockPart(int id);
    List<Stock> getAllStockParts();
    void updateStockPart(Stock part);
    void save(Stock part);
    Optional<Stock> getById(int id);

}
