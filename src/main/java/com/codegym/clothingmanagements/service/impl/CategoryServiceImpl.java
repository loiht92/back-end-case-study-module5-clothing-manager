package com.codegym.clothingmanagements.service.impl;

import com.codegym.clothingmanagements.models.Category;
import com.codegym.clothingmanagements.models.Clothing;
import com.codegym.clothingmanagements.repository.CategoryRepository;
import com.codegym.clothingmanagements.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findByClothingId(Long id, Pageable pageable) {
        return categoryRepository.findById(id, pageable);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(Category model) {
        categoryRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
