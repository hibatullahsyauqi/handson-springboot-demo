package com.bootcamp.handsonspringbootdemo.controller;

import com.bootcamp.handsonspringbootdemo.constant.ResponseMessage;
import com.bootcamp.handsonspringbootdemo.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public ResponseEntity<CommonResponse<String>> welcome() {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data("Welcome to Spring Restaurant!")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/about")
    public ResponseEntity<CommonResponse<Map<String, String>>> about() {
        Map<String, String> aboutData = new HashMap<>();
        aboutData.put("name", "Spring Restaurant");
        aboutData.put("description", "Spring Restaurant serves tasty food and beverages with affordable prices.");
        aboutData.put("version", "1.0.0");

        CommonResponse<Map<String, String>> response = CommonResponse.<Map<String, String>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(aboutData)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/endpoints")
    public ResponseEntity<CommonResponse<List<String>>> endpoints() {
        List<String> endPointsList = Arrays.asList("Endpoints are listed below.\n" +
                "- /api/menus - Menu",
                "- /api/menus/{id} - Menu detail",
                "- /api/menus/search?name=keyword - Search for a menu by its name",
                "- /api/menus/category/{category} - Filter menu by category"
        );

        CommonResponse<List<String>> response = CommonResponse.<List<String>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(endPointsList)
                .build();
        return ResponseEntity.ok(response);
    }
}
