package com.example.demo.controller;


import com.example.demo.entity.SubCategory;
import com.example.demo.repository.SubCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sub-categories")
public class SubCategoryController {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryController(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {

        Optional<SubCategory> existingSubCategory = subCategoryRepository.findByName(subCategory.getName());

        if(existingSubCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubCategory);
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable String id) {
        Optional<SubCategory> foundSubCategory = subCategoryRepository.findById(id);

        return foundSubCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable String id, SubCategory subCategory) {
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
    public ResponseEntity<Object> deleteSubCategory(@PathVariable String id) {
        return subCategoryRepository.findById(id)
                .map(subCategory -> {
                    subCategoryRepository.delete(subCategory);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
