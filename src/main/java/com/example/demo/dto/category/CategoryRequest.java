package com.example.demo.dto.category;


import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {
    private String title;
    private String description;
    private UUID creatorId;
}
