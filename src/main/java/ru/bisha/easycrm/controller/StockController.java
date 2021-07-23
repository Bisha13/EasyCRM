package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.Item;
import ru.bisha.easycrm.db.entity.Stock;
import ru.bisha.easycrm.service.StockService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @RequestMapping
    public String getAll(Model model) {
        var stocks = stockService.getAllStockParts();
        model.addAttribute("stockListAtr", stocks);
        return "allStocks";
    }

    @RequestMapping("/{id}")
    public String getById(@PathVariable("id") final int id, Model model) {
        var stock = stockService.getById(id);
        if (stock.isEmpty()) {
            return "redirect:/stocks";
        }
        model.addAttribute("stockAtr", stock.get());
        return "stock";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute("stockAtr") Stock stock) {
        stockService.save(stock);
        return "redirect:/stocks";
    }

    @RequestMapping("/delete")
    public String deleteStock(@RequestParam("itemId") final int id,
                                HttpServletRequest request) {
        if (id != 0) {
            stockService.deleteStockPart(id);
        }
        return "redirect:/stocks";
    }

    @RequestMapping("/new")
    public String createNew(Model model) {
        model.addAttribute("stockAtr", new Stock());
        return "stock";
    }
}
