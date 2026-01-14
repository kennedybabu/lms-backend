package com.example.demo.controller;


import com.example.demo.dto.subcategory.SubCategoryRequest;
import com.example.demo.dto.subcategory.SubCategoryResponse;
import com.example.demo.entity.SubCategory;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.service.subcategory.SubCategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sub-categories")
public class SubCategoryController {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryRepository subCategoryRepository, SubCategoryService subCategoryService) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    @CacheEvict(value = "subcategories", allEntries = true)
    public ResponseEntity<SubCategoryResponse> createSubCategory(@RequestBody SubCategoryRequest request) {
        try {
            SubCategoryResponse response = subCategoryService.createSubcategory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            if(e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @CacheEvict(value = "subcategories", allEntries = true)
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/{id}")
    @CacheEvict(value = "subcategories", allEntries = true)
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable UUID id) {
        Optional<SubCategory> foundSubCategory = subCategoryRepository.findById(id);

        return foundSubCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "subcategories", allEntries = true)
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable UUID id, SubCategory subCategory) {
        return subCategoryRepository.findById(id)
                .map(existingSubCategory -> {
                    existingSubCategory.setName(subCategory.getName());
                    existingSubCategory.setDescription(subCategory.getDescription());
                    existingSubCategory.setCategory(subCategory.getCategory());

                    SubCategory updatedSubCategory = subCategoryRepository.save(existingSubCategory);
                    return ResponseEntity.ok(updatedSubCategory);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "subcategories", key = "#id")
    public ResponseEntity<Object> deleteSubCategory(@PathVariable UUID id) {
        return subCategoryRepository.findById(id)
                .map(subCategory -> {
                    subCategoryRepository.delete(subCategory);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
