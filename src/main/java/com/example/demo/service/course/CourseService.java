package com.example.demo.service.course;

import com.example.demo.dto.course.CourseRequest;
import com.example.demo.dto.course.CourseResponse;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
}
