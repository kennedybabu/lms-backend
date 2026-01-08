package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Course;
import com.example.demo.entity.SubCategory;
import com.example.demo.entity.data.CourseLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByTitle(String title);

    Page<Course> findByCategory(Category category, Pageable pageable);

    Page<Course> findBySubcategory(SubCategory subCategory, Pageable pageable);

    Page<Course> findByLevel(CourseLevel courseLevel, Pageable pageable);

    Page<Course> findByInstructorId(UUID instructorsId, Pageable pageable);
}
