package com.chamath.TasteTown.Repositories;

import com.chamath.TasteTown.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    //for customers
    public List<Order> findByCustomerId(Long userId);

    //for restaurant owners
    public List<Order> findByRestaurantId(Long restaurantId);
}
