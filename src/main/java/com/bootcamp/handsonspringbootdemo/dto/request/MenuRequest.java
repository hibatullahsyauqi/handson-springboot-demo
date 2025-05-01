package com.bootcamp.handsonspringbootdemo.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private String name;
    private String description;
    private Double price;
    private String category;
    private boolean available;
}
