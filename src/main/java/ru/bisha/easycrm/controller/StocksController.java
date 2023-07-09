package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.StockEntity;
import ru.bisha.easycrm.db.repository.StockRepository;
import ru.bisha.easycrm.service.StocksService;

import java.util.List;

@RestController
@RequestMapping("/rest/stock")
@RequiredArgsConstructor
public class StocksController {

    private final StockRepository stockRepository;
    private final StocksService stocksService;

    @GetMapping("/all")
    public List<StockEntity> getAllItems() {
        List<StockEntity> stocks = stockRepository.findAll();
        stocks.stream()
                .filter(s -> StringUtils.hasLength(s.getName()))
                .forEach(s -> s.setName(s.getName().replaceAll("\\s+", " ").trim()));
        return stocks;
    }

    @GetMapping("/{id}")
    public StockEntity getById(@PathVariable String id) {
        return stocksService.getById(Integer.parseInt(id)).orElseThrow();
    }

    @PutMapping
    public void update(@RequestBody StockEntity stock) {
        stocksService.save(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        stocksService.deleteStockPart(id);
    }
}
