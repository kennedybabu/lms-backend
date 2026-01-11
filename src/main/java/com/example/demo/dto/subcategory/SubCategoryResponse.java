package com.example.demo.dto.subcategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubCategoryResponse(
        UUID id,
        String name,
        String description,
        UUID creatorId,
        UUID category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
