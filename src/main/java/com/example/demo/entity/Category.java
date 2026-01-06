package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany
    private List<SubCategory> subCategories;


}
