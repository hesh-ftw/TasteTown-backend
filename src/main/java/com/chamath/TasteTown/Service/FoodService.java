package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.Food;
import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Request.CreateFoodRequest;
import jdk.jfr.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Restaurant restaurant) throws Exception;

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isSeasonal );

    public List<Food> searchFood(String keyword);

    public Food updateAvailabilityStatus(Long foodId) throws Exception;

    public Food findFoodById(Long foodId) throws Exception;



}
