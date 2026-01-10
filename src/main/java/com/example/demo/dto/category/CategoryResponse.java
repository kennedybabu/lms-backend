package com.example.demo.dto.category;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String title,
        String description,
        UUID creatorId,
        String creatorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
