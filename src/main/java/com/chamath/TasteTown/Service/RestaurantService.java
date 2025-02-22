package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.DTO.RestaurantDto;
import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    //performed by restaurant owners
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restId, CreateRestaurantRequest updatedRest)throws Exception;

    public void deleteRestaurant(Long restId) throws Exception;

    public Restaurant updateRestaurantStatus(Long restId) throws Exception;



    //performed by customers
    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurantByName(String keyword);

    public Restaurant findRestaurantById(Long restId) throws Exception;

    public RestaurantDto addToFavorites(Long restId,User user) throws Exception;

    public List<RestaurantDto> getRestaurantsByUserId(Long userId) throws Exception;

    public void removeFromFavourite(Long resId, User user) throws Exception;
}
