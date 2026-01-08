package com.example.demo.dto;

import com.example.demo.entity.data.CourseLevel;
import lombok.Data;

import java.util.UUID;


@Data
public class CourseRequest {
    private UUID categoryId;
    private String title;
    private String description;
    private UUID subcategoryId;
    private UUID instructorId;
    private CourseLevel level;
}
