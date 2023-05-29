package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.StockEntity;
import ru.bisha.easycrm.db.repository.PartRepository;
import ru.bisha.easycrm.db.repository.StockRepository;
import ru.bisha.easycrm.service.StockService;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImp implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PartRepository partRepository;

    @Override
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

    @Override
    public List<StockEntity> getAllStockParts() {

        return stockRepository.findAll();
    }

    @Override
    public void updateStockPart(StockEntity part) {
        stockRepository.save(part);
    }

    @Override
    public void save(StockEntity part) {
        stockRepository.save(part);
    }

    @Override
    public Optional<StockEntity> getById(int id) {
        return stockRepository.findById(id);
    }
}
