package com.example.demo.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subcategory;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;
}
