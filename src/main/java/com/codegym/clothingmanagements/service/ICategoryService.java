package com.codegym.clothingmanagements.service;

import com.codegym.clothingmanagements.models.Category;
import com.codegym.clothingmanagements.models.Clothing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService extends GenerateService<Category> {
    Page<Category> findByClothingId(Long id, Pageable pageable);
}
