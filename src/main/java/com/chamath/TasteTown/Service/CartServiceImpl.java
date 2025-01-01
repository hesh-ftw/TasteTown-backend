package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.Cart;
import com.chamath.TasteTown.Model.CartItem;
import com.chamath.TasteTown.Model.Food;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Repositories.CartItemRepository;
import com.chamath.TasteTown.Repositories.CartRepository;
import com.chamath.TasteTown.Request.CartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    @Autowired
    CartItemRepository cartItemRepository;


    @Override
    public CartItem addItemToCart(CartItemRequest req, String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        Food food= foodService.findFoodById(req.getFoodId());
        Cart cart= cartRepository.findByCustomerId(user.getId());

        //if the food already in the cart -> update the quantity
        for(CartItem cartItem : cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity= cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        //if the food isn't available -> create a new cart item
        CartItem newCartItem= new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setTotalPrice((long) (req.getQuantity()*food.getPrice()));

        CartItem saveCartItem= cartItemRepository.save(newCartItem);
        cart.getItem().add(saveCartItem);

        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

            Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
            if(cartItemOptional.isEmpty()) {
                throw new Exception("cart item not fount");
            }
                CartItem item = cartItemOptional.get();
                item.setQuantity(quantity);

                //5*100=500
                item.setTotalPrice((long) (item.getFood().getPrice() * quantity));
                return cartItemRepository.save(item);

    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Cart cart= cartRepository.findByCustomerId(user.getId());

        CartItem cartItem= cartItemRepository.findCartItemById(cartItemId);
        if (cartItem == null || !cart.getItem().contains(cartItem)) {
            throw new Exception("Cart item not found or does not belong to this cart");
        }

        cartItemRepository.deleteById(cartItemId);
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public float calculateCartTotals(Cart cart) throws Exception {
        float total=0L;
        for(CartItem cartItem: cart.getItem()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart=cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("cart not found with id "+ id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart= cartRepository.findByCustomerId(userId);
        cart.setTotal((long) calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart= findCartByUserId(userId);
        if(cart==null){
            throw new Exception("cart is already empty");
        }
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
