package com.projeto.cardapio.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.cardapio.model.Food;
import com.projeto.cardapio.repository.FoodRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;
    
	@GetMapping
	public ResponseEntity<List<Food>> getAll(){
		return ResponseEntity.ok(foodRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Food> getById(@PathVariable Long id) {
	    return foodRepository.findById(id)
	            .map(food -> ResponseEntity.ok(food))
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Food>> findFoodByTitleContainingIgnoreCase(@PathVariable String title) {
	    List<Food> foods = foodRepository.findAllByTitleContainingIgnoreCase(title);
	    return ResponseEntity.ok(foods);
	}
	
	@PostMapping()
	public ResponseEntity<Food> createFood(@Valid @RequestBody Food food) {
	    Food savedFood = foodRepository.save(food);
	    return ResponseEntity.created(URI.create("/foods/" + savedFood.getId())).body(savedFood);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFood(@PathVariable("id") Long id, @RequestBody Food food) {
	    if (foodRepository.findById(id).isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    food.setId(id);
	    foodRepository.save(food);
	    return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) { 
		return foodRepository.findById(id)
				.map(resposta -> {
					foodRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}
}
