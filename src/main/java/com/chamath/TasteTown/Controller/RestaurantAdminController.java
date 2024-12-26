package com.chamath.TasteTown.Controller;

import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Request.CreateRestaurantRequest;
import com.chamath.TasteTown.Response.MessageResponse;
import com.chamath.TasteTown.Service.RestaurantService;
import com.chamath.TasteTown.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class RestaurantAdminController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String jwt) throws Exception{
        //validating user by jwt
        User user= userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.createRestaurant(request,user);
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id,
                                             @RequestHeader("Authorization") String jwt,
                                             CreateRestaurantRequest request) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.updateRestaurant(id,request);
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@PathVariable Long id,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(id);
        MessageResponse msg= new MessageResponse();
        msg.setMessage("restaurant successfully deleted ");
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.updateRestaurantStatus(id);
        MessageResponse msg= new MessageResponse();
        msg.setMessage("restaurant status updated ");

        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

}
