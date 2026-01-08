package com.example.demo.service;

import com.example.demo.dto.CourseRequest;
import com.example.demo.dto.CourseResponse;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
}
