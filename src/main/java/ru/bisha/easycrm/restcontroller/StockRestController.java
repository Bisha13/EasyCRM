package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.StockEntity;
import ru.bisha.easycrm.db.repository.StockRepository;
import ru.bisha.easycrm.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/rest/stock")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ui", havingValue = "rest")
public class StockRestController {

    private final StockRepository stockRepository;
    private final StockService stockService;

    @GetMapping("/all")
    public List<StockEntity> getAllItems() {
        List<StockEntity> stocks = stockRepository.findAll();
        stocks.stream()
                .filter(s -> StringUtils.hasLength(s.getName()))
                .forEach(s -> s.setName(s.getName().replaceAll("\\s+", " ")));
        return stocks;
    }

    @GetMapping("/{id}")
    public StockEntity getById(@PathVariable String id) {
        return stockService.getById(Integer.parseInt(id)).orElseThrow();
    }

    @PutMapping
    public void update(@RequestBody StockEntity stock) {
        stockService.save(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        stockService.deleteStockPart(id);
    }
}
