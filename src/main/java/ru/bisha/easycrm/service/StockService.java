package ru.bisha.easycrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.StockEntity;
import ru.bisha.easycrm.db.repository.PartRepository;
import ru.bisha.easycrm.db.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PartRepository partRepository;

    public void deleteStockPart(int id) {
        var stock = stockRepository.findById(id);
        if (stock.isEmpty()) {
            return;
        }
        var parts = partRepository.getAllByStockId(id);
        parts.forEach(p -> {
            p.setIsStock(false);
            p.setPurchasePrice(stock.get().getPurchase());
            p.setName(stock.get().getName());
            p.setPrice(stock.get().getPrice());
            p.setStock(stockRepository.findById(0).orElseThrow());
        });
        stockRepository.deleteById(id);
    }

    public List<StockEntity> getAllStockParts() {

        return stockRepository.findAll();
    }

    public void updateStockPart(StockEntity part) {
        stockRepository.save(part);
    }

    public void save(StockEntity part) {
        stockRepository.save(part);
    }

    public Optional<StockEntity> getById(int id) {
        return stockRepository.findById(id);
    }
}
