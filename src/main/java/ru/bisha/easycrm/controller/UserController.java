package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.UserEntity;
import ru.bisha.easycrm.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@ConditionalOnProperty(value = "ui", havingValue = "thymeleaf")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String getAll(Model model) {
        var users = userService.getAllUsers();
        model.addAttribute("userListAtr", users);
        return "allUsers";
    }

    @RequestMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        Optional<UserEntity> user = userService.getById(id);
        if (user.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("userAtr", user.get());
        return "user";
    }

    @RequestMapping("/save")
    public String saveUser(@ModelAttribute("itemAtr") UserEntity user) {
        userService.save(user);
        return "redirect:/users";
    }

    @RequestMapping("/delete")
    public String deleteItem(@RequestParam("itemId") final int id) {
        if (id != 0) {
            userService.delete(id);
        }
        return "redirect:/users";
    }

    @RequestMapping("/new")
    public String createNew(Model model) {
        model.addAttribute("userAtr", new UserEntity());
        return "user";
    }


}
