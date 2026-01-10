package com.example.demo.service.course;

import com.example.demo.dto.course.CourseRequest;
import com.example.demo.dto.course.CourseResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Course;
import com.example.demo.entity.SubCategory;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        if(courseRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new RuntimeException("Course with title '" + request.getTitle() + "' already exists");
        }

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setLevel(request.getLevel());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        course.setCategory(category);

        SubCategory subCategory = subCategoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        course.setSubcategory(subCategory);

        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        course.setInstructor(instructor);

        Course savedCourse = courseRepository.save(course);

        return mapToResponse(savedCourse);
    }

    private CourseResponse mapToResponse(Course course) {
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


