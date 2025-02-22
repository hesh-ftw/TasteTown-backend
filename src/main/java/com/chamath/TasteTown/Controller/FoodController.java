package com.chamath.TasteTown.Controller;

import com.chamath.TasteTown.Model.Food;
import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Request.CreateFoodRequest;
import com.chamath.TasteTown.Response.MessageResponse;
import com.chamath.TasteTown.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    FoodService foodService;

    @PostMapping("/create")
    public ResponseEntity<Food> createFood(@RequestHeader("Authorization") String jwt,
                                           @RequestBody CreateFoodRequest request) throws Exception{

        User user= userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.findRestaurantById(request.getRestaurantId());
        Food food= foodService.createFood(request, restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long foodId) throws Exception{

        User user= userService.findUserByJwtToken(jwt);
        foodService.deleteFood(foodId);

        MessageResponse response= new MessageResponse();
        response.setMessage("food successfully deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity <List<Food>> getRestaurantFoods(@RequestHeader("Authorization") String jwt,
//                                                   @RequestParam boolean vegetarian,
//                                                   @RequestParam boolean seasonal,
                                                   @PathVariable Long restaurantId) throws Exception{

        User user= userService.findUserByJwtToken(jwt);

        List<Food> food= foodService.getRestaurantsFood(restaurantId);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }


    @PutMapping("/{foodId}")
    public ResponseEntity<MessageResponse> updateAvailability(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long foodId) throws Exception{

        User user= userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(foodId);

        MessageResponse response= new MessageResponse();
        response.setMessage("food status updated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String jwt,
                                                 @RequestParam String keyword) throws Exception{

        User user= userService.findUserByJwtToken(jwt);

        List<Food> food= foodService.searchFood(keyword);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
