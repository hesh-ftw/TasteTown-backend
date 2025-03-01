package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f WHERE f.name LIKE %:keyword%") //food searching query
    List<Food>searchFood(@Param("keyword") String keyword);



}
