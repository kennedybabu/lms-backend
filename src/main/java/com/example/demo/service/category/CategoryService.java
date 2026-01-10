package com.example.demo.service.category;

import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
}
