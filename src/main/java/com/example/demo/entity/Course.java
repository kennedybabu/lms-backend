package com.example.demo.entity;


import com.example.demo.entity.data.CourseLevel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "course_title", unique = true)
    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subcategory;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @Enumerated
    private CourseLevel level;

    @Transient
    private Double rating;

    @Transient
    private Integer totalStudents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
