package com.example.demo.service.subcategory;

import com.example.demo.dto.subcategory.SubCategoryRequest;
import com.example.demo.dto.subcategory.SubCategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.SubCategory;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SubCategoryResponse createSubcategory(SubCategoryRequest request) {
        if(subCategoryRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Subcategory with the name '" + request.getName() + "' exists");
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.getName());
        subCategory.setDescription(request.getDescription());

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        subCategory.setCreator(creator);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subCategory.setCategory(category);

        subCategory.setCategory(category);

        SubCategory savedSubcategory = subCategoryRepository.save(subCategory);
        return mapToResponse(savedSubcategory);

    }

    private SubCategoryResponse mapToResponse(SubCategory subCategory) {
        return new SubCategoryResponse(
                subCategory.getId(),
                subCategory.getName(),
                subCategory.getDescription(),
                subCategory.getCreator().getId(),
                subCategory.getCategory().getId(),
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt()
        );
    }
}
