package com.chamath.TasteTown.Repositories;

import com.chamath.TasteTown.Model.Restaurant;
import com.chamath.TasteTown.Service.RestaurantService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findByOwnerId(Long userId);

    List<Restaurant> findByNameContainingIgnoreCase(String keyword);
}
