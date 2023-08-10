package com.drunar.coincorner.http.controller;


import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAll(Model model, UserFilter filter){
        model.addAttribute("users",userService.findAll(filter));
        System.out.println();
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDTO user) {
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping
    public String create(@ModelAttribute UserCreateEditDTO user, RedirectAttributes redirectAttributes) {
//        if (true) { TODO: validation
//            redirectAttributes.addFlashAttribute("user", user);
//            return "redirect:/users/registration";
//        }
        return "redirect:/users/" + userService.create(user).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDTO user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}
