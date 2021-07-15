package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Stock;

import java.util.List;

public interface StockService {

    void deleteStockPart(int id);
    List<Stock> getAllStockParts();
    void updateStockPart(Stock part);
    void addStockPart(Stock part);

}
