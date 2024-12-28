package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.Food;
import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;


    @Override
    public Food createFood(CreateFoodRequest req, Restaurant restaurant) throws Exception {

        Food newFood= new Food();
        newFood.setRestaurant(restaurant);
        newFood.setName(req.getName());
        newFood.setDescription(req.getDescription());
        newFood.setPrice(req.getPrice());
        newFood.setVegetarian(req.isVegetarian());
        newFood.setSeasonal(req.isSeasonal());
        newFood.setImages(req.getImages());

        Food savedFood =foodRepository.save(newFood);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {

        Food food= findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isSeasonal) {

        List<Food> foods= foodRepository.findByRestaurantId(restaurantId);
        
        if(isSeasonal){
            foods= filterBySeasonal(foods,isSeasonal);
        }
        if(isVegetarian){
            foods= filterByVegetarian(foods,isVegetarian);
        }

        return foods;
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()== isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }


    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food= findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood =foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("Food is not exist..");
        }

        return optionalFood.get();
    }
}
