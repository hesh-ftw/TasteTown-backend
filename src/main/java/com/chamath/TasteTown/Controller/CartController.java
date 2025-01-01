package com.chamath.TasteTown.Controller;

import com.chamath.TasteTown.Model.Cart;
import com.chamath.TasteTown.Model.CartItem;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Request.UpdateCartItemRequest;
import com.chamath.TasteTown.Request.CartItemRequest;
import com.chamath.TasteTown.Service.CartService;
import com.chamath.TasteTown.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    UserService userService;

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItem(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception{

        Cart cart=cartService.removeItemFromCart(id,jwt);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @PutMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Cart cart= cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Cart cart= cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }





}
