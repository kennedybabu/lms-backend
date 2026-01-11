package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subcategories")
@Data
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "subcategory_name", unique = true)
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "sub_creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private List<Course> courses = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
