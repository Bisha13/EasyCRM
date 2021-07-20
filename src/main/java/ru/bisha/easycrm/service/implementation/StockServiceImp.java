package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Stock;
import ru.bisha.easycrm.db.repository.StockRepository;
import ru.bisha.easycrm.service.StockService;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImp implements StockService {

    @Autowired
    private StockRepository repository;

    @Override
    public void deleteStockPart(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Stock> getAllStockParts() {

        return repository.findAll();
    }

    @Override
    public void updateStockPart(Stock part) {
        repository.save(part);
    }

    @Override
    public void save(Stock part) {
        repository.save(part);
    }

    @Override
    public Optional<Stock> getById(int id) {
        return repository.findById(id);
    }
}