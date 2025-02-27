package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.*;
import com.chamath.TasteTown.Repositories.AddressRepository;
import com.chamath.TasteTown.Repositories.OrderItemRepository;
import com.chamath.TasteTown.Repositories.OrderRepository;
import com.chamath.TasteTown.Repositories.UserRepository;
import com.chamath.TasteTown.Request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CartService cartService;


    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shipAddress= order.getDeliveryAddress();
        Address savedAddress= addressRepository.save(shipAddress);

        //check the address already added in user's account if not add it
        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant= restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(String.valueOf(new Date()));
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setOrderStatus("pending");
        createdOrder.setRestaurant(restaurant);

        Cart cart= cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems= new ArrayList<>();

        for(CartItem cartItem: cart.getItem()){
            OrderItem orderItem= new OrderItem();

            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(Float.valueOf(cartItem.getTotalPrice()));

            OrderItem savedOrderItem= orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotal());

        Order savedOrder= orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status") ;
    }


    @Override
    public void cancelOrder(Long orderId) throws Exception {
        //Order order= findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        List<Order> orders= orderRepository.findByCustomerId(userId);
        Collections.reverse(orders);
        return orders;
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders= orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
       Optional<Order> order= orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new RuntimeException("there is no order exist with the id"+orderId);
        }
        return order.get();
    }
}
