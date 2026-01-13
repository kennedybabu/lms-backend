package com.example.demo.dto.course;

import com.example.demo.entity.data.CourseLevel;

import java.time.LocalDateTime;
import java.util.UUID;


public record CourseResponse (
    UUID id,
    String title,
    String description,
    UUID categoryId,
    String categoryTitle,
    UUID subcategoryId,
    String subcategoryName,
    UUID instructorId,
    String instructorName,
    CourseLevel level,
    Double rating,
    Integer totalStudents,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){}
