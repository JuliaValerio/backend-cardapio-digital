package com.projeto.cardapio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.projeto.cardapio.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
	List<Food> findAllByTitleContainingIgnoreCase(@Param("title") String title);
}
