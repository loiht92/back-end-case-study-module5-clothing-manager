package com.codegym.clothingmanagements.controllers;

import com.codegym.clothingmanagements.models.Clothing;
import com.codegym.clothingmanagements.service.IClothingService;
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

    @PostMapping("/clothing")
    public ResponseEntity<Void> createClothing(@RequestBody Clothing clothing, UriComponentsBuilder builder) {
        clothingService.save(clothing);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/clothing/{id}").buildAndExpand(clothing.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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
