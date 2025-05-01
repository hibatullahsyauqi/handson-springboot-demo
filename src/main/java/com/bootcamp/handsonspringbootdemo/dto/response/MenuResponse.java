package com.bootcamp.handsonspringbootdemo.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private boolean available;
}
