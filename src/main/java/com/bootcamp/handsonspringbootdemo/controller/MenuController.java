package com.bootcamp.handsonspringbootdemo.controller;

import com.bootcamp.handsonspringbootdemo.constant.ResponseMessage;
import com.bootcamp.handsonspringbootdemo.dto.request.MenuRequest;
import com.bootcamp.handsonspringbootdemo.dto.response.CommonResponse;
import com.bootcamp.handsonspringbootdemo.dto.response.MenuResponse;
import com.bootcamp.handsonspringbootdemo.model.Menu;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private MenuResponse convertToMenuResponse(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getCategory(),
                menu.isAvailable()
        );
    }

    private List<MenuResponse> convertToMenuResponseList(List<Menu> menuList) {
        return menuList.stream()
                .map(this::convertToMenuResponse)
                .collect(Collectors.toList());
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenu() {
        List<MenuResponse> menuResponses = convertToMenuResponseList(menuList);
        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuById(@PathVariable String id) {
        Menu menu = findMenuById(id);

        if (menu == null) {
            CommonResponse<MenuResponse> errorResponse = CommonResponse.<MenuResponse>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message(ResponseMessage.ERROR_NOT_FOUND)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        MenuResponse menuResponse = convertToMenuResponse(menu);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<MenuResponse>>> searchMenuByName(@RequestParam String name) {
        List<Menu> filteredMenuList = menuList.stream()
                .filter(menu -> menu.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        List<MenuResponse> menuResponses = convertToMenuResponseList(filteredMenuList);

        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getMenuByCategory(@PathVariable String category) {
        List<Menu> filteredMenus = menuList.stream()
                .filter(menu -> menu.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        List<MenuResponse> menuResponses = convertToMenuResponseList(filteredMenus);

        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<MenuResponse>> createMenu(@RequestBody MenuRequest request) {
        Menu menu = new Menu(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.isAvailable()
        );

        menuList.add(menu);
        MenuResponse menuResponse = convertToMenuResponse(menu);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(menuResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(@PathVariable String id, @RequestBody MenuRequest request) {
        Menu existingMenu = findMenuById(id);

        if (existingMenu == null) {
            CommonResponse<MenuResponse> errorResponse = CommonResponse.<MenuResponse>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message(ResponseMessage.ERROR_NOT_FOUND)
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        existingMenu.setName(request.getName());
        existingMenu.setDescription(request.getDescription());
        existingMenu.setPrice(request.getPrice());
        existingMenu.setCategory(request.getCategory());
        existingMenu.setAvailable(request.isAvailable());

        MenuResponse menuResponse = convertToMenuResponse(existingMenu);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(menuResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteMenu(@PathVariable String id) {
        Menu existingMenu = findMenuById(id);

        if (existingMenu == null) {
            CommonResponse<String> errorResponse = CommonResponse.<String>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message(ResponseMessage.ERROR_NOT_FOUND)
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        menuList.removeIf(menu -> menu.getId().equals(id));

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .data("Menu with the ID " + id + " has been deleted")
                .build();

        return ResponseEntity.ok(response);
    }

    private Menu findMenuById(String id) {
        return menuList.stream()
                .filter(menu -> menu.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
