package com.chamath.TasteTown.Controller;

import com.chamath.TasteTown.DTO.RestaurantDto;
import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Request.CreateRestaurantRequest;
import com.chamath.TasteTown.Service.RestaurantService;
import com.chamath.TasteTown.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantUserController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @PostMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestParam String keyword,
                                                       @RequestHeader("Authorization") String jwt) throws Exception{

        User user= userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurant= restaurantService.searchRestaurantByName(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestHeader("Authorization") String jwt) throws Exception{

        User user= userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurant= restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,
                                                              @PathVariable Long id) throws Exception{

        User user= userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/fav/{RestId}")
    public ResponseEntity<RestaurantDto> addToFavourite(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long RestId) throws Exception{

        User user= userService.findUserByJwtToken(jwt);

        RestaurantDto fav= restaurantService.addToFavorites(RestId, user);

        return new ResponseEntity<>(fav, HttpStatus.OK);
    }

    @DeleteMapping("/fav/remove/{restId}")
    public ResponseEntity<?> removeFromFavourite(@RequestHeader("Authorization") String jwt,
                                                 @PathVariable Long restId) throws Exception {

            User user = userService.findUserByJwtToken(jwt);
            restaurantService.removeFromFavourite(restId, user);

            return new ResponseEntity<>("Restaurant removed from favorites.", HttpStatus.OK);
    }

    @GetMapping("/favourites/{userId}")
    public ResponseEntity<List<RestaurantDto>> getFavRestaurantsByUserId(@RequestHeader("Authorization") String jwt,
                                                                         @PathVariable Long userId ) throws Exception {
        userService.findUserByJwtToken(jwt);

        List<RestaurantDto> getFav= restaurantService.getRestaurantsByUserId(userId);

        return new ResponseEntity<>(getFav,HttpStatus.OK);
    }





}
