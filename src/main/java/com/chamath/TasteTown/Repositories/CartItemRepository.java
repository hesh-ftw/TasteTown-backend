package com.chamath.TasteTown.Repositories;

import com.chamath.TasteTown.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    public CartItem findCartItemById(Long itemId);

     void deleteById(Long cartItemId);
}
