package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.Cart;
import com.chamath.TasteTown.Model.CartItem;
import com.chamath.TasteTown.Request.CartItemRequest;

public interface CartService {

    public CartItem addItemToCart(CartItemRequest req, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt)throws Exception;

    public float calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userId)throws Exception;

    public Cart clearCart(Long userId)throws Exception;
}
