package com.example.demo.service.subcategory;

import com.example.demo.dto.subcategory.SubCategoryRequest;
import com.example.demo.dto.subcategory.SubCategoryResponse;

public interface SubCategoryService {
    SubCategoryResponse createSubcategory(SubCategoryRequest request);
}
