package com.example.demo.service.category;

import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new RuntimeException("Category with title '" + request.getTitle() + "' already exists");
        }

        Category category = new Category();
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        category.setCreator(creator);

        Category savedCategory = categoryRepository.save(category);
        return mapToCategory(savedCategory);
    }

    private CategoryResponse mapToCategory(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getCreator().getId(),
                category.getCreator().getFirstName() + " " + category.getCreator().getLastName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

}
