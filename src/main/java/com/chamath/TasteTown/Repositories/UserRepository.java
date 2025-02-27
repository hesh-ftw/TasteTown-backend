package com.chamath.TasteTown.Repositories;

import com.chamath.TasteTown.DTO.RestaurantDto;
import com.chamath.TasteTown.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String username);

    public RestaurantDto findFavouritesById(Long userId);
}
