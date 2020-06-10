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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ClothingController {
    @Autowired
    private IClothingService clothingService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/clothing")
    public ResponseEntity<Page<Clothing>> getAllClothing(Pageable pageable) {
        Page<Clothing> clothingList = clothingService.findAll(pageable);
        if (clothingList.getTotalElements() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clothingList, HttpStatus.OK);
    }

    @GetMapping("/clothing/{id}")
    public ResponseEntity<Optional<Clothing>> getClothingById(@PathVariable Long id) {
        Optional<Clothing> clothing = clothingService.findById(id);
        if (! clothing.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clothing, HttpStatus.OK);
    }

    @PostMapping("/clothing/{category_id}")
    public ResponseEntity<Object> createClothing(@PathVariable Long category_id, @RequestBody Clothing clothing) {
        ResponseObject<Clothing> responseObject = new ResponseObject<>();
        try {
            Category category = categoryService.findById(category_id)
                    .orElseThrow(() -> new CustomException("CATEGORY ID NOT FOUND !"));

            clothing.setCategory(category);
            clothingService.save(clothing);
            responseObject.setData(clothing);
        }catch (CustomException e) {
            responseObject.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(responseObject, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/clothing/{id}")
    public ResponseEntity<Optional<Clothing>> updateClothing(@PathVariable Long id, @RequestBody Clothing clothing) {
        Optional<Clothing> clothingOptional = clothingService.findById(id);
        if (! clothingOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clothingService.save(clothing);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/clothing/{id}")
    public ResponseEntity<Optional<Clothing>> deleteClothing(@PathVariable Long id) {
        Optional<Clothing> clothing = clothingService.findById(id);
        if (! clothing.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clothingService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
