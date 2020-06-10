package com.codegym.clothingmanagements.controllers;

import com.codegym.clothingmanagements.CustomException;
import com.codegym.clothingmanagements.models.Category;
import com.codegym.clothingmanagements.models.Clothing;
import com.codegym.clothingmanagements.service.ICategoryService;
import com.codegym.clothingmanagements.service.IClothingService;
import com.codegym.clothingmanagements.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IClothingService clothingService;

    @GetMapping("/category")
    public ResponseEntity<Page<Category>> getAllCategory(Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        if (categories.getTotalElements() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/category/{clothing_id}")
    public ResponseEntity<Object> createCategory(@PathVariable Long clothing_id, @RequestBody Category category) {
        ResponseObject<Category> responseObject = new ResponseObject<>();
        try {
            Clothing clothing = clothingService.findById(clothing_id)
                    .orElseThrow(() -> new CustomException("CLOTHING ID NOT FOUND !"));

            category.setClothing(clothing);
            categoryService.save(category);
            responseObject.setData(clothing);
        }catch (CustomException e) {
            responseObject.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(responseObject, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        ResponseObject<Category> responseObject = new ResponseObject<>();

        Optional<Category> categoryOptional = categoryService.findById(id);
        if (! categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.save(category);
        responseObject.setData(category);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        ResponseObject<Category> responseObject = new ResponseObject<>();

        Category category = categoryService.findById(id).orElseThrow(() -> new CustomException("CATEGORY ID NOT FOUND !"));

        categoryService.remove(id);
        responseObject.setData(category);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
