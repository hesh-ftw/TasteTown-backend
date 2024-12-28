package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.DTO.RestaurantDto;
import com.chamath.TasteTown.Model.Address;
import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Repositories.AddressRepository;
import com.chamath.TasteTown.Repositories.RestaurantRepository;
import com.chamath.TasteTown.Repositories.UserRepository;
import com.chamath.TasteTown.Request.CreateRestaurantRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address= addressRepository.save(req.getAddress());

        Restaurant newRestaurant= new Restaurant();
            newRestaurant.setId(req.getId());
            newRestaurant.setName(req.getName());
            newRestaurant.setAddress(address);
            newRestaurant.setDescription(req.getDescription());
            newRestaurant.setCuisineType(req.getCuisineType());
            newRestaurant.setContactInformation(req.getContactInformation());
            newRestaurant.setImages(req.getImages());
            newRestaurant.setOpeningHrs(req.getOpeningHours());
            newRestaurant.setOwner(user);

        return restaurantRepository.save(newRestaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restId, CreateRestaurantRequest updatedRest) throws Exception {
        Restaurant restaurant= findRestaurantById(restId);

            if(restaurant.getCuisineType() !=null){
                restaurant.setCuisineType(updatedRest.getCuisineType());
            }
            if(restaurant.getAddress() !=null){
                restaurant.setAddress(updatedRest.getAddress());
            }
            if(restaurant.getDescription() !=null){
                restaurant.setDescription(updatedRest.getDescription());
            }
            if(restaurant.getName() !=null){
                restaurant.setName(updatedRest.getName());
            }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restId) throws Exception {
        Restaurant restaurant= findRestaurantById(restId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurantByName(String keyword) {
        return restaurantRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restId) throws Exception {
        Optional<Restaurant> find= restaurantRepository.findById(restId);

        if(find.isEmpty()){
            throw new Exception("restaurant not found with id : "+restId);
        }
        return find.get();
    }

    @Override
    public RestaurantDto addToFavorites(Long restId, User user) throws Exception {

        Restaurant restaurant= findRestaurantById(restId);

        RestaurantDto dto= new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setId(restaurant.getId());
        dto.setTitle(restaurant.getName());
        dto.setImage(restaurant.getImages());

        if(user.getFavourites().contains(dto)){
            user.getFavourites().remove(dto);
        } else
            user.getFavourites().add(dto);

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restId) throws Exception {
        Restaurant restaurant= findRestaurantById(restId);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
