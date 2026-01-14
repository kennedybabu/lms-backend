package com.example.demo.controller;


import com.example.demo.dto.course.CourseRequest;
import com.example.demo.dto.course.CourseResponse;
import com.example.demo.entity.Course;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.course.CourseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public CourseController(CourseRepository courseRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, UserRepository userRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    //crud operations
    @PostMapping
    @CacheEvict(value = "courses", allEntries = true)
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest request) {
        try {
            CourseResponse response = courseService.createCourse(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @CacheEvict(value = "courses", allEntries = true)
    public ResponseEntity<Course> getCourseById(@PathVariable UUID id) {
        Optional<Course> foundCourse = courseRepository.findById(id);

        return foundCourse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @CacheEvict(value = "courses", allEntries = true)
    public ResponseEntity<Page<CourseResponse>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CourseResponse> courses = courseRepository.findAll(pageable)
                .map(this::toResponse);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<Page<Course>> getInstructorsCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam UUID instructorsId
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Course> courses = courseRepository.findByInstructorId(instructorsId, pageable);
        return ResponseEntity.ok(courses);
    }
    

    @PutMapping("/{id}")
    @CacheEvict(value = "courses", allEntries = true)
    public ResponseEntity<Course> updateCourse(@PathVariable UUID id,@RequestBody Course course) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setTitle(course.getTitle());
                    existingCourse.setDescription(course.getDescription());
                    existingCourse.setLevel(course.getLevel());

                    Course updatedCourse = courseRepository.save(existingCourse);
                    return ResponseEntity.ok(updatedCourse);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "courses", key = "#id")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID id) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseRepository.delete(course);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategory().getId(),
                course.getCategory().getTitle(),
                course.getSubcategory().getId(),
                course.getSubcategory().getName(),
                course.getInstructor().getId(),
                course.getInstructor().getFirstName() + " " + course.getInstructor().getLastName(),
                course.getLevel(),
                course.getRating(),
                course.getTotalStudents(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}
