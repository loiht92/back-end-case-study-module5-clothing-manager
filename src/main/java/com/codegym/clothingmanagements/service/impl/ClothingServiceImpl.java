package com.codegym.clothingmanagements.service.impl;

import com.codegym.clothingmanagements.models.Clothing;
import com.codegym.clothingmanagements.repository.ClothingRepository;
import com.codegym.clothingmanagements.service.IClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClothingServiceImpl implements IClothingService {
    @Autowired
    private ClothingRepository clothingRepository;

    @Override
    public Page<Clothing> findAll(Pageable pageable) {
        return clothingRepository.findAll(pageable);
    }

    @Override
    public Optional<Clothing> findById(Long id) {
        return clothingRepository.findById(id);
    }

    @Override
    public void save(Clothing model) {
        clothingRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        clothingRepository.deleteById(id);
    }
}
