package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.entity.Stock;
import ru.bisha.easycrm.db.repository.StockRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/stock")
@RequiredArgsConstructor
public class StockRestController {

    private final StockRepository stockRepository;

    @GetMapping("/all")
    public List<Stock> getAllItems() {
        return stockRepository.findAll();
    }
}
