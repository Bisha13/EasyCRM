package ru.bisha.easycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bisha.easycrm.db.entity.Item;
import ru.bisha.easycrm.db.entity.User;
import ru.bisha.easycrm.service.PartService;
import ru.bisha.easycrm.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/users")
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
        Optional<User> user = userService.getById(id);
        if (user.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("userAtr", user.get());
        return "user";
    }

    @RequestMapping("/save")
    public String saveUser(@ModelAttribute("itemAtr") User user) {
        userService.save(user);
        return "redirect:/users/" + user.getId();
    }

    @RequestMapping("/delete")
    public String deleteItem(@RequestParam("itemId") final int id,
                                HttpServletRequest request) {
        if (id != 0) {
            userService.delete(id);
        }
        return "redirect:" + Optional.of(request.getHeader("referer"))
                .orElse("/users/");
    }

    @RequestMapping("/new")
    public String createNew(Model model) {
        model.addAttribute("userAtr", new User());
        return "user";
    }


}
