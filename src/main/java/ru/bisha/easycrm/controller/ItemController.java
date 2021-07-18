package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.Item;
import ru.bisha.easycrm.service.CategoryService;
import ru.bisha.easycrm.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping
    public String getAll(Model model) {
        var items = itemService.getAll();
        model.addAttribute("itemsAtr", items);
        return "allItems";
    }

    @RequestMapping("/{id}")
    public String getItem(@PathVariable int id, Model model) {
        var item = itemService.getById(id);
        if (item.isEmpty()) {
            return "redirect:/items";
        }
        var categories = categoryService.getAll();
        model.addAttribute("itemAtr", item.get());
        model.addAttribute("categoriesAtr", categories);
        return "item";
    }

    @RequestMapping("/save")
    public String saveItem(@ModelAttribute("itemAtr") Item item) {
        itemService.save(item);
        return "redirect:/items/" + item.getId();
    }

    @RequestMapping("/delete")
    public String deleteService(@RequestParam("itemId") final int id,
                                HttpServletRequest request) {
        if (id != 0) {
            itemService.delete(id);
        }
        return "redirect:" + Optional.of(request.getHeader("referer"))
                .orElse("/items/");
    }

    @RequestMapping("/new")
    public String createNew(Model model) {
        var categories = categoryService.getAll();
        model.addAttribute("categoriesAtr", categories);
        model.addAttribute("itemAtr", new Item());
        return "item";
    }
}
