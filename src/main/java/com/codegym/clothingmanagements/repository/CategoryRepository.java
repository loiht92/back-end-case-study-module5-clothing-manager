package com.codegym.clothingmanagements.repository;

import com.codegym.clothingmanagements.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Page<Category> findById(Long id, Pageable pageable);
}
