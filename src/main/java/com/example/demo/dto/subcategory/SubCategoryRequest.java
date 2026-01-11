package com.example.demo.dto.subcategory;

import lombok.Data;

import java.util.UUID;

@Data
public class SubCategoryRequest {
    private String name;
    private String description;
    private UUID creatorId;
    private UUID categoryId;
    private String categoryName;
    private String creatorName;
}
