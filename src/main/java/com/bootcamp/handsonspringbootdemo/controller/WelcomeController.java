package com.bootcamp.handsonspringbootdemo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Spring Restaurant API";
    }

    @GetMapping("/about")
    public String about() {
        return "Spring Restaurant serves variety of tasty foods and beverages with affordable prices.";
    }

    @GetMapping("/endpoints")
    public String endpoints() {
        return "Endpoints are listed below.\n" +
                "- /api/menus\t\t\t(Menu)\n" +
                "- /api/menus/{id}\t\t\t(Menu detail)\n" +
                "- /api/menus/search?name=keyword\t\t\t(Search for a menu by its name)\n" +
                "- /api/menus/category/{category}\t\t\t(Filter menu by category)";
    }
}
