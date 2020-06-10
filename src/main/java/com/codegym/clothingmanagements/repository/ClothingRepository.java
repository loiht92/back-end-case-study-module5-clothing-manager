package com.codegym.clothingmanagements.repository;

import com.codegym.clothingmanagements.models.Clothing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepository extends PagingAndSortingRepository<Clothing, Long> {
}
