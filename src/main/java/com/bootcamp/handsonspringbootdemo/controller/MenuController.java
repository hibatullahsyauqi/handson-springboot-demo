package com.bootcamp.handsonspringbootdemo.controller;

import com.bootcamp.handsonspringbootdemo.model.Menu;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    // Database simulation with List
    private final List<Menu> menuList = new ArrayList<>();

    public MenuController() {
        Menu friedRice = new Menu(
                UUID.randomUUID().toString(),
                "Oriental Fried Rice",
                "Fried rice with scrambled egg, shredded chicken breast, and veggies",
                29000.00,
                "Main course",
                true
        );
        Menu friedNoodle = new Menu(
                UUID.randomUUID().toString(),
                "Fried Noodle",
                "Fried noodle with meatballs, egg, and veggies",
                33000.00,
                "Main course",
                true
        );
        Menu everySummertime = new Menu(
                UUID.randomUUID().toString(),
                "Every Summertime",
                "Fresh tangerine mixed with grape and peach",
                17000.00,
                "Beverage",
                true
        );
        menuList.add(friedRice);
        menuList.add(friedNoodle);
        menuList.add(everySummertime);
    }

    @GetMapping
    public List<Menu> getAllMenu() {
        return menuList;
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable String id) {
        return menuList.stream()
                .filter(menu -> menu.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/search")
    public List<Menu> getMenuByName(@RequestParam String name) {
        return menuList.stream()
                .filter(menu -> menu.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    public List<Menu> getMenuByCategory(@PathVariable String category) {
        return menuList.stream()
                .filter(menu -> menu.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        menu.setId(UUID.randomUUID().toString());
        menuList.add(menu);
        return menu;
    }

    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable String id, @RequestBody Menu updatedMenu) {
        for (Menu menu : menuList) {
            if (menu.getId().equals(id)) {
                updatedMenu.setId(id);
                menuList.set(menuList.indexOf(updatedMenu), updatedMenu);
                return updatedMenu;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteMenu(@PathVariable String id) {
        return menuList.removeIf(menu -> menu.getId().equals(id));
    }
}
